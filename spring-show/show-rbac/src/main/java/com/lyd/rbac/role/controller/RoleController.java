package com.lyd.rbac.role.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.qvo.ResourcesQuery;
import com.lyd.rbac.resources.service.ResourcesService;
import com.lyd.rbac.role.domain.RoleResDO;
import com.lyd.rbac.role.domain.SysRoleVO;
import com.lyd.rbac.role.qvo.RoleQuery;
import com.lyd.rbac.role.service.RoleService;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DateUtil;
import com.lyd.utils.Json;
import com.lyd.utils.Page;
import com.lyd.utils.ValidatorUtil;
import com.lyd.utils.excel.ExcelUtil_xls;
import com.lyd.utils.excel.ExcelUtil_xlsx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "角色管理API", tags = "角色管理API")
@Controller
@RequestMapping(value = "/user/role_do/")
public class RoleController {

	@Autowired
	RoleService roleService;
	@Autowired
	ResourcesService resourcesService;
	
	@ApiOperation(value = "跳转到角色管理主页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/user/role/index";
	}
	
	@ApiOperation(value = "角色分页列表", notes = "JSON", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageNow", value = "当前页数", required = true, dataType = "java.lang.Integer"),
		@ApiImplicitParam(name = "qvo", value = "查询页数", required = true, dataType = "RoleQuery")
	})
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute RoleQuery qvo,
			@RequestParam(name = "pageNow", defaultValue = "0", required = false) Integer pageNow ) throws Exception{
		qvo.setBlurred(true);
		if(qvo.getName() != null){
			qvo.setName(qvo.getName().trim());
		}
		Page page = new Page();
		page.setCurPage(pageNow);
		page = roleService.list(page, qvo);
		page.init();
		return page;
	}
	
	
	@ApiOperation(value = "角色列表", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "roleList.do", method = RequestMethod.GET)
	public Map<String, Object> select() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleSelect", roleService.list(new RoleQuery()));
		return map;
	}
	
	@ApiOperation(value = "获取单个role", notes = " ", produces = MediaType.TEXT_HTML_VALUE)
	@ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "java.lang.String")
	@ResponseBody
	@RequestMapping(value = "get.do", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> get(@RequestParam(name = "id", required = true) String id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		SysRoleVO role = roleService.get(id);
		List<SysResourcesVO> menusVo = resourcesService.getRolesByRoleId(id);
		List<String> list = new ArrayList<String>();
		for (SysResourcesVO r : menusVo) {
			list.add(r.getId());
		}
		role.setMenuIdList(list);
		result.put("role", role);
		ResponseEntity<Map<String, Object>> entity = new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		return entity;
	}
	/*public SysRoleVO get(@RequestParam(name = "id", required = true) String id) throws Exception{
		SysRoleVO role = roleService.get(id);
		List<SysResourcesVO> menusVo = resourcesService.getRolesByRoleId(id);
		List<String> list = new ArrayList<String>();
		for (SysResourcesVO r : menusVo) {
			list.add(r.getId());
		}
		role.setMenuIdList(list);
		return role;
	}
	*/
	
	@ApiOperation(value = "更新角色", notes = "只更新名称、描述、系统唯一标识、状态", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public Json update(@RequestBody SysRoleVO role){
		Json json = new Json();
		json.setSuccess(true);
		try {
			BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
			BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
			updateMap.put("name", role.getName());
			updateMap.put("roleKey", role.getRoleKey());
			updateMap.put("description", role.getDescription());
			updateMap.put("state", role.getState());
			whereMap.put("id", role.getId());
			roleService.update(updateMap, whereMap);
			//权限设置
			List<SysResourcesVO> resList = resourcesService.list(new ResourcesQuery());
			List<RoleResDO> list = new ArrayList<RoleResDO>();
			for (SysResourcesVO res : resList) {
				RoleResDO rr = new RoleResDO();
				rr.setResId(res.getId());
				rr.setRoleId(role.getId());
				rr.setState("1"); //禁用
				list.add(rr);
			}
			if (role.getMenuIdList() != null && !role.getMenuIdList().isEmpty()) {
				for (String resid : role.getMenuIdList()) {
					for (RoleResDO rr : list) {
						if (rr.getResId().equals(resid)) {
							rr.setState("0");
						}
					}
				}
			}
			roleService.saveOrUpdateRole_Res(list);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功，重新登录系统生效！");
		return json;
	}
	
	@ApiOperation(value = "新增角色", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	public Json create(@RequestBody SysRoleVO role){
		Json json = new Json();
		json.setSuccess(true);
		ValidatorUtil.validateEntity(role);
		try {
			roleService.save(role);
			String roleId = role.getId();
			//权限设置
			List<SysResourcesVO> resList = resourcesService.list(new ResourcesQuery());
			List<RoleResDO> list = new ArrayList<RoleResDO>();
			for (SysResourcesVO res : resList) {
				RoleResDO rr = new RoleResDO();
				rr.setResId(res.getId());
				rr.setRoleId(roleId);
				rr.setState("1"); //禁用
				list.add(rr);
			}
			if (role.getMenuIdList() != null && !role.getMenuIdList().isEmpty()) {
				for (String resid : role.getMenuIdList()) {
					for (RoleResDO rr : list) {
						if (rr.getResId().equals(resid)) {
							rr.setState("0");
						}
					}
				}
			}
			roleService.saveOrUpdateRole_Res(list);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			json.setMsg("操作失败，已存在该记录");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功，重新登录系统生效！");
		return json;
	}
	
	@ApiOperation(value = "根据roleID删除角色", notes = "逻辑删除", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "ids", value = "要删除的角色的ID", required = true, dataType = "java.lang.String")
	@ResponseBody
	@RequestMapping(value = "remove.do", method = RequestMethod.POST)
	public Json remove(@RequestParam(name = "ids", required = true) String ids){
		Json json = new Json();
		json.setSuccess(true);
		try {
			String[] roleIds = ids.trim().split(",");
			for (String roleId : roleIds) {
				roleService.removeById(roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "角色信息导出", notes = "角色信息导出")
	@RequestMapping(value = "export.do", method = RequestMethod.GET)
	public void export(@ModelAttribute RoleQuery qvo, HttpServletResponse response) throws Exception{
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(("角色信息表"+DateUtil.getDate()+"").getBytes("GB2312"), "8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] headers = { "角色名","状态","roleKey","描述" };
		String[] columns = { "name","state","roleKey","description" };
		
		//准备数据
		qvo.setBlurred(true);
		if(qvo.getName() != null){
			qvo.setName(qvo.getName().trim());
		}
		List<SysRoleVO> roleList = roleService.list(qvo);
		for (SysRoleVO role : roleList) {
			if("0".equals(role.getState())){
				role.setState("正常");
			} else if("1".equals(role.getState())){
				role.setState("禁用");
			} else {
				role.setState("非法状态，请向负责人反馈！");
			}
		}
		try {
			if(roleList !=null && !roleList.isEmpty()){
				if(roleList.size()<65534){
					ExcelUtil_xls<SysRoleVO> excelUtil = new ExcelUtil_xls<SysRoleVO>();
					excelUtil.exportExcel("角色信息表", headers, columns, roleList, out, "");
				}else{
					ExcelUtil_xlsx<SysRoleVO> excelUtil = new ExcelUtil_xlsx<SysRoleVO>();
					excelUtil.exportExcel("角色信息表", headers, columns, roleList, out, "");
				}
			}else{
				ExcelUtil_xls<SysRoleVO> excelUtil = new ExcelUtil_xls<SysRoleVO>();
				excelUtil.exportExcel("角色信息表", headers, columns, roleList, out, "");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 }
}
