package com.lyd.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import com.lyd.core.support.SocialUserInfo;
import com.lyd.email.sendemail.SendEmailInter;
import com.lyd.email.sendemail.SendQQEmailImpl;
import com.lyd.rbac.support.controller.LoginProxyController;
import com.lyd.rbac.support.domain.LoginProxy;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.service.UserService;
import com.lyd.util.web.RbacConstant;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DateUtil;
import com.lyd.utils.DesUtils;
import com.lyd.utils.Json;
import com.lyd.utils.constant.SystemConstant;
import com.lyd.utils.constant.UserStateConstant;
import com.lyd.utils.web.ServletUtil;
import com.lyd.utils.web.VrifyCodeUtil;

import io.swagger.annotations.ApiOperation;

@Controller
public class SocialController {

	private RequestCache requestCache = new HttpSessionRequestCache();
	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	UserService userService;

	/*
	 * @RequestMapping("/authentication/require")
	 * 
	 * @ResponseStatus(code = HttpStatus.UNAUTHORIZED) public String
	 * requireAuthentication(HttpServletRequest request, HttpServletResponse
	 * response, ModelMap map) { SavedRequest savedRequest =
	 * requestCache.getRequest(request, response); if (savedRequest != null) {
	 * String targetUrl = savedRequest.getRedirectUrl(); return "redirect:" +
	 * targetUrl; } // map.put("error", "访问的服务需要身份认证"); // return "redirect:/";
	 * System.out.println("需要认证"); return "/"; }
	 */
	
	/*@ResponseBody
	@GetMapping("/social/user")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		SocialUserInfo userInfo = new SocialUserInfo();
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}*/

	/*
	 * 在SocialConfig.java中配置
	 */
	@ApiOperation(value = "注册或者绑定页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/thirdpart/login_do/toregist.do", method = RequestMethod.GET)
	public String toregist(HttpServletRequest request) throws Exception {
		// 如果已经绑定， 那就直接登陆吧
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		// 不知道那里配置权限到springsecurity 中了 。配置本地系统有问题。。
		List<String> userId = userService.findUserIdsWithConnection(connection);
		if (userId.size() == 1) {
			LoginProxy proxy = LoginProxyController.loginByUserId(request, userId.get(0), null);
			if (proxy.isSuccess()) {
				return "redirect:" + proxy.getRedirectUrl();
			}
		}
		return "/thirdpart/bindlogin";
	}
	
	@ApiOperation(value = "跳转到绑定注册页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/thirdpart/login_do/tobindregister.do", method = RequestMethod.GET)
	public String toBindRegist() throws Exception {

		return "/thirdpart/bindregister";
	}
	
	@ApiOperation(value = "跳转到绑定登录页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/thirdpart/login_do/tobindthird.do", method = RequestMethod.GET)
	public String toBindLogin() throws Exception {

		return "/thirdpart/bindlogin";
	}

	// 第三方 账号绑定
	@ResponseBody
	@PostMapping("/thirdpart/thirdpart_do/bindthird.do")
	public Json bind(HttpServletRequest httpServletRequest, ModelMap map) throws Exception {
		Json j = new Json();
		j.setMsg("操作成功");
		j.setSuccess(true);
		Connection<?> connection = providerSignInUtils
				.getConnectionFromSession(new ServletWebRequest(httpServletRequest));
		if (connection.getKey().getProviderId() == null) {
			j.setMsg("操作失败，请返回主页重新操作！");
			j.setSuccess(false);
			return j;
			// 会话失效 或者是被攻击
		}
		// 首先检测验证码
		if (!VrifyCodeUtil.checkVrifyCode(httpServletRequest, map)) {
			j.setMsg("验证码错误");
			j.setSuccess(false);
			return j;
		}
		String username = ServletUtil.getStringParamDefaultBlank(httpServletRequest, "username");
		String password = ServletUtil.getStringParamDefaultBlank(httpServletRequest, "password");
		LoginProxy proxy = LoginProxyController.proxyLogin(httpServletRequest, username, password, null);
		if (proxy.isSuccess()) {
			SocialUserInfo userInfo = new SocialUserInfo();
			userInfo.setProviderId(connection.getKey().getProviderId());
			userInfo.setProviderUserId(connection.getKey().getProviderUserId());
			userInfo.setNickname(connection.getDisplayName());
			userInfo.setHeadimg(connection.getImageUrl());
			providerSignInUtils.doPostSignUp(proxy.getUser().getId(), new ServletWebRequest(httpServletRequest));
			j.setMsg("绑定成功,去首页可以登录了！");
			j.setSuccess(true);
			return j;
		} else {
			j.setMsg(proxy.getResult());
			j.setSuccess(false);
			return j;
		}
	}

	// 第三方登陆 注册新用户绑定方式
	@ResponseBody
	@PostMapping(value = { "/thirdpart/login_do/bindregister.do", "/signup" })
	public Json regist(UserVO user, HttpServletRequest request,
			@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = VrifyCodeUtil.PARAMETERNAME, required = true) String vrifyCode, HttpSession session)
			throws Exception {
		Json json = new Json();
		json.setMsg("操作成功");
		json.setSuccess(true);
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		if (connection.getKey().getProviderId() == null) {
			json.setMsg("操作失败，请返回主页从重新操作！");
			json.setSuccess(false);
			return json;
			// 会话失效 或者是被攻击
		}
		// 不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
		if (!VrifyCodeUtil.checkvrifyCode(vrifyCode, session)) {
			json.setSuccess(true);
			json.setMsg("验证码不正确！");
			return json;
		}
		json.setSuccess(true);
		json.setMsg("我们将发送邮箱到您的邮箱中进行验证，大约3小时左右不验证将删除注册信息");
		String now = DateUtil.getDateTime();
		user.setCredentialsSalt(new DesUtils().encrypt(user.getPassword()));
		user.setPassword(RbacConstant.getPwd(user.getPassword()));
		user.setRoleId("10");
		user.setPhone("");
		user.setState(UserStateConstant.DIE);
		user.setCreateTime(now);
		try {
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("创建失败，已存在该用户");
			return json;
		}
		// 发送邮件
		// <a
		// href='www.???.com/common/login_do/relife.do?userid=?&salt=?'>激活</a>";
		// String content = "点此激活：<a href='" + SystemConstant.getSystemDomain()
		// + "/common/login_do/relife.do?userid=" + user.getId() + "&salt=" +
		// user.getCredentialsSalt() + "'>激活账户</a>";
		String url = SystemConstant.getSystemDomain() + "/common/login_do/relife.do?userid=" + user.getId()
				+ "&username=" + user.getUsername() + "&salt=" + user.getCredentialsSalt();
		String content = this.getActiveContent(url, user.getUsername()); // 获取激活邮件的hmtl内容

		try {
			// 用QQ邮箱发送
			SendEmailInter send = new SendQQEmailImpl();
			send.sendMail(email, SystemConstant.getSystemName() + "注册激活", content);
		} catch (Exception e) {
			e.printStackTrace();
			BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
			whereMap.put("id", user.getId());
			userService.remove(whereMap);
			json.setMsg("发送邮件失败，可能被提供方拦截，再试一次或者换一种邮箱类型");
			return json;
		}
		providerSignInUtils.doPostSignUp(user.getId(), new ServletWebRequest(request));
		return json;
	}
	
	/** 获取拼接的激活邮件的内容  */
	private String getActiveContent(String activeurl, String username) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><head>");
		buffer.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
		buffer.append("<base target=\"_blank\" />");
		buffer.append("</head>");
		buffer.append("<body>尊敬的");
		buffer.append(username);
		buffer.append(":<br>请点击此处激活您的账户：");
		buffer.append("<a href=" + activeurl + ">激活账户</a>");
		buffer.append("<br>");
		buffer.append("为保障您的帐号安全，请在3小时内点击该链接<br>");
		buffer.append("如无法点击请您将下面链接复制到浏览器地址栏访问:<br><span style=\"color:blue\">" + activeurl + "</span><br><br><br>");
		buffer.append("若如果您已激活，请忽略本邮件，由此给您带来的不便请谅解。<br>");
		buffer.append("本邮件由系统自动发出，请勿直接回复！ ");
		buffer.append("</body></html>");
		return buffer.toString();
	}

}
