package com.lyd.rbac.user.controllor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.excel.ExcelUtil;
import com.lyd.rbac.user.qvo.UserQuery;
import com.lyd.rbac.user.service.UserService;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DateUtil;
import com.lyd.utils.Json;
import com.lyd.utils.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "用户管理API", tags = "用户管理API")
@Controller
@RequestMapping(value = "/user/user_do/")
public class UserController {

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "跳转到用户管理页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/user/user/index";
	}
	
	@ApiOperation(value = "用户列表-分页", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNow", value = "当前页数", required = true, dataType = "java.lang.Integer"),
		@ApiImplicitParam(name = "qvo", value = "用户查询条件", required = false, dataType = "UserQvo")
	})
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute UserQuery qvo, @ApiIgnore ModelMap map,
			@RequestParam(name = "pageNow", defaultValue = "0", required = false) Integer pageNow) throws Exception{
		qvo.setBlurred(true);
		if(qvo.getUsername() != null){
			qvo.setUsername(qvo.getUsername().trim());
		}
		Page page = new Page();
		page.setCurPage(pageNow);
		page = userService.list(page, qvo);
		List<UserVO> list = (List<UserVO>)page.getResult();
		//安全起见,对密码做处理
		for (UserVO user : list) {
			user.setPassword("");
			user.setCredentialsSalt("");
		}
		page.init();
		return page;
	}
	
	@ApiOperation(value = "获取单个用户信息", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@ApiImplicitParam(name = "id", value = "用户ID", dataType = "java.lang.String", required = true)
	@RequestMapping(value = "get.do", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> get(@RequestParam(name = "id", required = true) String id) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		UserVO user = userService.get(id);
		//敏感信息禁止泄露
		user.setPassword("");
		user.setCredentialsSalt("");
		result.put("user", user);
		ResponseEntity<Map<String, Object>> map = new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		return map;
	}
		
	/** 更新用户信息 **/
	@ApiOperation(value = "更新用户信息", notes = "只更新角色，用户状态(封号等)，email ,手机号", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public Json update(@RequestBody UserVO user) {
		Json json = new Json();
		json.setSuccess(true);
		BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
		BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
		updateMap.put("phone", user.getPhone());
		updateMap.put("email", user.getEmail());
		updateMap.put("roleid", user.getRoleId());
		updateMap.put("state", user.getState());
		whereMap.put("id", user.getId());
		try {
			userService.update(updateMap, whereMap);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "ids", value = "用户ID", required = true, dataType = "java.lang.String")
	@ResponseBody
	@RequestMapping(value = "remove.do", method = RequestMethod.POST)
	public Json remove(@RequestParam(name = "ids", required = true) String ids){
		Json json = new Json();
		json.setSuccess(true);
		String[] userIds = ids.trim().split(",");
		try {
			for (String id : userIds) {
				userService.removeById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "导出用户", notes = "导出用户")
	@ResponseBody
	@RequestMapping(value = "export.do", method = RequestMethod.GET)
	public void export(@ModelAttribute UserQuery qvo, HttpServletResponse response) throws Exception{
		qvo.setBlurred(true);
		response.setContentType("application/vnd.ms-excel");
		String filename = "用户信息导出" + DateUtil.getDate();
		filename = java.net.URLEncoder.encode(filename, "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");
		ServletOutputStream out = response.getOutputStream();
		ExcelUtil<UserVO> util = new ExcelUtil<UserVO>(UserVO.class);// 创建工具类.
		util.exportExcel(userService.list(qvo), "用户信息", out, 2, null, true); //导出
	}
	
	
	//
	// /*** 新增 **/
	// @ResponseBody
	// @RequestMapping("create.do")
	// //@SystemLog(module = "用户管理", methods = "创建用户")
	// public Json create(HttpServletRequest request, HttpServletResponse
	// response, ModelMap map) {
	// Json j = new Json();
	// j.setMsg("操作成功");
	// j.setSuccess(true);
	// PasswordHelper passwordHelper = new PasswordHelper();
	// User usernoid = getServletUser(request);
	// usernoid.setPassword("123456789");
	// passwordHelper.encryptPassword(usernoid);
	// // User usernoid = getServletUser(request);
	// // usernoid.setPassword(EncryptUtil.MD5("123456789"));
	// try {
	// userService.createandid(usernoid);
	// } catch (Exception e) {
	// e.printStackTrace();
	// j.setMsg("操作失败");
	// }
	// return j;
	// }
	/** 检测账号是否存在 **/
	/*@ApiOperation(value = "检测账号是否存在", notes = "要求传入帐号，email,手机号做检测", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = { "isexist.do" }, method = { RequestMethod.GET, RequestMethod.POST })
	public boolean isexist(@ModelAttribute UserQuery qvo) {
		return userService.checkisExist(qvo);
	}*/
	
	
}
