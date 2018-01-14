package com.lyd.rbac.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import java.net.UnknownHostException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.email.sendemail.SendEmailInter;
import com.lyd.email.sendemail.SendQQEmailImpl;
import com.lyd.rbac.resources.service.ResourcesService;
import com.lyd.rbac.support.controller.LoginProxyController;
import com.lyd.rbac.support.domain.LoginProxy;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.qvo.UserQuery;
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

/*** 用Shiro登陆 **/
@Api(tags = "后台登录API")
@Controller
@RequestMapping(value = "/common/login_do/")
public class LoginControllor {
	
	@Autowired
	UserService				userService;
	@Autowired
	ResourcesService		resourcesService;
	@Autowired
	LoginService			loginService;
	@Autowired
	AuthenticationManager	authenticationManager;
	
	/**
	 * 跳转登录页面
	 * @author lyd
	 * @date 2017年9月6日
	 * @param map
	 * @return
	 * @throws UnknownHostException 
	 */
	@ApiOperation(value = "跳转登录页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "tologin.do", method = RequestMethod.GET)
	public String tologin(ModelMap map){
		map.put("systemIcp", SystemConstant.getICP());
		map.put("systemName", SystemConstant.getSystemName());
		map.put("systemDomain", SystemConstant.getSystemDomain());
		return "/login";
	}
	
	/**
	 * 用户登录
	 * @author lyd
	 * @date 2017年9月6日
	 * @param httpServletRequest
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户登录 ", notes = "", produces = MediaType.ALL_VALUE)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "username", value = "帐号", dataType = "java.lang.String", required = true), 
		@ApiImplicitParam(name = "password", value = "密码", dataType = "java.lang.String", required = true)
	})
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request, ModelMap map) throws Exception {
		//首先校验验证码
		if(!VrifyCodeUtil.checkVrifyCode(request, map)){
			return "/login";
		}
		String username = ServletUtil.getStringParamDefaultBlank(request, "username");
		String password = ServletUtil.getStringParamDefaultBlank(request, "password");
		LoginProxy proxy = LoginProxyController.proxyLogin(request, username, password, null);
		if(proxy.isSuccess()){
			return "redirect:" + proxy.getRedirectUrl();
		} else {
			map.put("error", "用户或密码不正确！");
			return "/login";
		}
	}
	
	@ApiOperation(value = "无权限提示页面 ", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = { "unauthorizedUrl.do" }, method = RequestMethod.GET)
	public String unauthorizedUrl() throws Exception {
		return "/denied";
	}
	
	/**
	 * 跳转到注册页面
	 * @author lyd
	 * @date 2017年9月7日
	 * @return
	 */
	@ApiOperation(value = "跳转到注册页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "toregister.do", method = RequestMethod.GET)
	public String toregister() {
		return "/register";
	}
	
	/**
	 * 检测用户是否已存在
	 * @author lyd
	 * @date 2017年9月12日
	 * @param userQvo
	 * @return
	 */
	@ApiOperation(value = "检测账号是否存在", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "isexist.do", method = RequestMethod.POST)
	public boolean isExist(UserQuery userQvo){
		return userService.checkIsExist(userQvo);
	}
	
	/**
	 * 用户注册
	 * @author lyd
	 * @date 2017年9月11日
	 * @param user
	 * @param session
	 * @param email
	 * @param vrifyCode
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户注册", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "register.do", method = RequestMethod.GET)
	public Json register(UserVO user, HttpSession session, 
		@RequestParam(name = "email", required = true) String email,
		@RequestParam(name = VrifyCodeUtil.PARAMETERNAME, required = true) String vrifyCode
	) throws Exception{
		Json json = new Json();
		//验证码校验
		if(!VrifyCodeUtil.checkvrifyCode(vrifyCode, session)){
			json.setSuccess(true);
			json.setMsg("验证码不正确！");
			return json;
		}
		json.setSuccess(true);
		json.setMsg("我们将发送邮件到您的邮箱中，请在3小时内完成验证！");
		String now = DateUtil.getDateTime();
		//user.setPassword(new DesUtils(user.getUsername() + now).encrypt(user.getPassword()));
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
			json.setMsg("创建失败,该用户已存在！");
			return json;
		}
		
		//发送邮件
		//<a href='www.???.com/common/login_do/relife.do?userid=?&salt=?'>激活</a>";
		//String content = "点此激活：<a href='" + SystemConstant.getSystemDomain() + "/common/login_do/relife.do?userid=" + user.getId() + "&salt=" + user.getCredentialsSalt() + "'>激活账户</a>";
		String url = SystemConstant.getSystemDomain() + "/common/login_do/relife.do?userid=" + user.getId() + "&username="+user.getUsername()+"&salt=" + user.getCredentialsSalt();
		String content =this.getActiveContent(url,user.getUsername()); //获取激活邮件的hmtl内容
		
		try {
			//用QQ邮箱发送
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
		return json;
	}
	
	/**
	 * 邮箱激活
	 * @author lyd
	 * @date 2017年9月8日
	 * @param map
	 * @param username
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "激活邮箱页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "relife.do", method = RequestMethod.GET)
	public String relife(ModelMap map, 
		@RequestParam(name = "username", required = true) String username,
		@RequestParam(name = "salt", required = true) String salt
	) throws Exception{
		UserQuery qvo = new UserQuery();
		qvo.setUsername(username);
		qvo.setState(UserStateConstant.DIE);
		List<UserVO> list = userService.list(qvo);
		if(list!=null && list.size()==1){
			BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
			BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
			updateMap.put("state", UserStateConstant.OK);
			whereMap.put("username", list.get(0).getUsername());
			userService.update(updateMap, whereMap);
			map.put("error", "激活成功，现在可以登录啦！");
			return "/login";
		}
		map.put("error", "该链接已失效！");
		return "/login";
	}
	
	/**
	 * 退出系统
	 * @author lyd
	 * @date 2017年9月11日
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "退出系统", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "loginout.do", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/common/login_do/tologin.do";
	}
	
	/**
	 * 忘记密码
	 * @author lyd
	 * @date 2017年9月12日
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "忘记密码", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({@ApiImplicitParam(name = "username", value = "账号", dataType = "java.lang.String", required = true)})
	@ResponseBody
	@RequestMapping(value = "forgetpwd.do", method = RequestMethod.GET)
	public Json forgetPwd(@RequestParam(name = "username", required = true) String username) throws Exception{
		Json json = new Json();
		json.setSuccess(true);
		UserQuery userQvo = new UserQuery();
		userQvo.setUsername(username);
		List<UserVO> list = userService.list(userQvo);
		if(list==null || list.size()==0){
			json.setSuccess(false);
			json.setMsg("无此账号");
			return json;
		}
		UserVO user = list.get(0);
		if((UserStateConstant.LOCK).equals(user.getState())){
			json.setSuccess(false);
			json.setMsg("账号被锁，无法使用！");
			return json;
		}
		if((UserStateConstant.DIE).equals(user.getState())){
			json.setSuccess(false);
			json.setMsg("账号未激活，无法使用！");
			return json;
		}
		if(!(UserStateConstant.OK).equals(user.getState())){
			json.setSuccess(false);
			json.setMsg("未知原因，无法使用！");
			return json;
		}
		//上线系统防止用户修改admin密码
		if("admin".equals(user.getUsername())){
			json.setSuccess(false);
			json.setMsg("您无权修改超级管理员账号密码！");
			return json;
		}
		
		// 加密 的字符串 防止 用户知道自己的ID
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uid", user.getId());
		jsonObj.put("today", DateUtil.getDate());
		jsonObj.put("username", username);
		String encryptInfo = jsonObj.toString();
		encryptInfo = "encryptInfo=" + new DesUtils().encrypt(encryptInfo);
		String content = "<a href='" + SystemConstant.getSystemDomain() + "/common/login_do/resetpwd.do?" + encryptInfo + "'>重置密码</a><br>有效期截止到当天晚上24:00";
		try{
			SendEmailInter send = new SendQQEmailImpl();
			send.sendMail(user.getEmail(), SystemConstant.getSystemName()+"密码重置", content);
		} catch (Exception e){
			e.printStackTrace();
			json.setMsg("发送邮件失败，可能被提供方拦截！");
			return json;
		}
		json.setMsg("发送邮件成功，请到邮箱重置密码！");
		return json;
	}
	
	/**
	 * 重置密码页面
	 * @author lyd
	 * @date 2017年9月12日
	 * @param encryptInfo
	 * @param model
	 * @return
	 */
	@ApiOperation("重置密码页面")
	@ApiImplicitParams({@ApiImplicitParam(name = "encryptInfo", value = "加密信息", dataType = "java.lang.String", required = true)})
	@RequestMapping(value = "resetpwd.do", method = RequestMethod.GET)
	public String resetPwdInit(@RequestParam(name = "encryptInfo", required = true) String encryptInfo, Model model){
		try {
			JSONObject jsonObject = JSONObject.fromObject(new DesUtils().decrypt(encryptInfo));
			String userId = jsonObject.getString("uid");
			Object today = jsonObject.get("today");
			Object username = jsonObject.get("username");
			if(DateUtil.getDate().equals(today)){
				UserVO user = userService.get(userId);
				if((UserStateConstant.LOCK).equals(user.getState())){
					return "/lock";
				}
				if((UserStateConstant.DIE).equals(user.getState())){
					return "/die";
				}
				if(!(UserStateConstant.OK).equals(user.getState())){
					return "";
				}
				if((UserStateConstant.LOCK).equals(user.getState())){
					return "/lock";
				}
				model.addAttribute("encryptInfo", encryptInfo);
				model.addAttribute("username", username);
				return "/reset";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "加载信息错误！");
			return "/fail";
		}
		model.addAttribute("msg", "该链接已过期！");
		return "/fail";
	}
	

	/**
	 * 重置密码
	 * @author lyd
	 * @date 2017年9月12日
	 * @param encryptInfo
	 * @param password
	 * @param model
	 */
	@ApiOperation(value = "重置密码", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "encryptInfo", value = "加密信息", dataType = "java.lang.String", required = true),
		@ApiImplicitParam(name = "password", value = "密码", dataType = "java.lang.String", required = true)
	})
	@ResponseBody
	@RequestMapping(value = "resetpassword.do", method = RequestMethod.POST)
	public Json resetPassword(@RequestParam(name = "encryptInfo", required = true) String encryptInfo,
			@RequestParam(name = "password", required = true) String password, ModelMap model){
		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("操作成功！");
		try {
			JSONObject fromObject = JSONObject.fromObject(new DesUtils().decrypt(encryptInfo));
			String userId = fromObject.getString("uid");
			String today = fromObject.getString("today");
			if(!DateUtil.getDate().equals(today)){
				json.setMsg("操作失败，已超时！");
				json.setSuccess(false);
				return json;
			}
			UserVO user = userService.get(userId);
			if(user == null){
				json.setSuccess(false);
				json.setMsg("无此账号");
				return json;
			}
			if((UserStateConstant.LOCK).equals(user.getState())){
				json.setSuccess(false);
				json.setMsg("账号被锁，无法使用！");
				return json;
			}
			if((UserStateConstant.DIE).equals(user.getState())){
				json.setSuccess(false);
				json.setMsg("账号未激活，无法使用！");
				return json;
			}
			if(!(UserStateConstant.OK).equals(user.getState())){
				json.setSuccess(false);
				json.setMsg("未知原因，无法使用！");
				return json;
			}
			//上线系统防止用户修改admin密码
			if("admin".equals(user.getUsername())){
				json.setSuccess(false);
				json.setMsg("您无权重置超级管理员账号密码！");
				return json;
			}
			BaseMap<String, Object> updatemap = new BaseMap<String, Object>();
			BaseMap<String, Object> wheremap = new BaseMap<String, Object>();
			updatemap.put("password", RbacConstant.getPwd(password));
			updatemap.put("credentialssalt", new DesUtils().encrypt(password));
			wheremap.put("id", user.getId());
			userService.update(updatemap, wheremap);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败！");
			return json;
		}
		return json;
	}
	
	/**
	 * 清除过期没有激活的用户 
	 * 		每3小时执行一次
	 */
	@Scheduled(cron = "0 0 */3 * * ?")
	public void cleanuser() throws Exception{
		userService.removeExpired();
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
