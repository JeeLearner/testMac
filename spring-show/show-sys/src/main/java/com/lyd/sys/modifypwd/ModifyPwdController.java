package com.lyd.sys.modifypwd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.service.UserService;
import com.lyd.util.web.RbacConstant;
import com.lyd.util.web.SessionUtil;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DateUtil;
import com.lyd.utils.DesUtils;
import com.lyd.utils.Json;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "修改密码API")
@Controller
@RequestMapping(value = "/sys/modifypwd_do")
public class ModifyPwdController {

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "跳转到密码修改页面", notes = "")
	@RequestMapping(value = "/index.do")
	public String toModifyPwd(){
		return "/system/modifypwd/index";
	}
	
	@ApiOperation(value = "修改密码", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "/modifypwd.do")
	public Json modifyPwd(@AuthenticationPrincipal UserVO user, 
			@RequestParam(name = "password0", required = true) String oldPassword,
			@RequestParam(name = "password", required = true) String password) throws Exception{
		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("操作成功");
		//UserVO user = SessionUtil.findUserSession();
		if(user == null){
			json.setMsg("您尚未登录，请先登录！");
			return json;
		}
		if (password.length() <= RbacConstant.MIN_PASSWORD_LENTH) {
			json.setMsg("新密码太短");
			return json;
		}
		if(!user.getCredentialsSalt().equals(new DesUtils().encrypt(oldPassword))){
			json.setMsg("原密码错误，请重新输入！");
			return json;
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		BaseMap<String, Object> updatemap = new BaseMap<String, Object>();
		BaseMap<String, Object> wheremap = new BaseMap<String, Object>();
		//updatemap.put("password", new DesUtils(user.getUsername() + user.getCreatetime()).encrypt(user.getPassword()));
		updatemap.put("password", RbacConstant.getPwd(password));
		updatemap.put("credentialssalt", new DesUtils().encrypt(password));
		wheremap.put("id", user.getId());
		userService.update(updatemap, wheremap);
		return json;
	}
}
