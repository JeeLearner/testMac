package com.lyd.rbac.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lyd.rbac.resources.service.ResourcesService;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.util.web.SessionUtil;
import com.lyd.util.web.RbacConstant;
import com.lyd.utils.constant.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="后台首页API")
@Controller
@RequestMapping(value = "/common/index_do/")
public class IndexController {
	
	@Autowired
	ResourcesService resourcesService;
	
	/**
	 * 加载首页
	 * @author lyd
	 * @date 2017年9月6日
	 * @param model
	 * @return
	 * @throws Exception
	 */
	// 新版index 拆分结构
	@ApiOperation(value = "后台首页 ", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(@AuthenticationPrincipal UserVO user, Model model) throws Exception {
		//UserVO user = (UserVO) SessionUtil.findUserSession();
		if (user == null) {
			return "redirect:/common/login_do/tologin.do";
		}
		model.addAttribute("systemname", SystemConstant.getSystemName());
		model.addAttribute("systemauth", SystemConstant.getSystemAuth());
		model.addAttribute("systemicp", SystemConstant.getICP());
		model.addAttribute("systemdomain", SystemConstant.getSystemDomain());
		model.addAttribute("userFormMap", user);
		return "/index/admin/index";
	}
	
	/** 加载菜单 **/
	@ApiOperation(value = "后台首页-菜单 ", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "menu.do", method = RequestMethod.GET)
	public String menu(@AuthenticationPrincipal UserVO user, ModelMap map) {
		//UserVO user = (UserVO) SessionUtil.findUserSession();
		if (user == null) {
			return "";
		}
		map.put("userFormMap", user);
		if (RbacConstant.IsAdmin()) {
			return "/index/admin/menu";
		}
		if (RbacConstant.IsOther(user)) {
			return "/index/other/menu";
		}
		return "";
	}
	
	/** 加载头部 **/
	@ApiOperation(value = "后台首页-头部", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "head.do", method = RequestMethod.GET)
	public String head(@AuthenticationPrincipal UserVO user, ModelMap map) {
		//UserVO user = (UserVO) SessionUtil.findUserSession();
		if (user == null) {
			return "";
		}
		map.put("userFormMap", user);
		return "/index/admin/head";
	}
	
	/** 加载欢迎页 **/
	@ApiOperation(value = "后台首页-欢迎页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "welcome.do", method = RequestMethod.GET)
	public String welcome(@AuthenticationPrincipal UserVO user, ModelMap map) {
		//UserVO user = (UserVO) SessionUtil.findUserSession();
		if (user == null) {
			return "";
		}
		map.put("userFormMap", user);
		if(RbacConstant.IsAdmin()){
			return "/index/admin/welcome";
		}
		if (RbacConstant.IsOther(user)) {
			// 让用户选择子系统
			return "/index/other/welcome";
		}
		return "";
	}
}
