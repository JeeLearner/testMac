package com.lyd.rbac.resources.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.functors.TruePredicate;
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

import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.qvo.ResourcesQuery;
import com.lyd.rbac.resources.service.ResourcesService;
import com.lyd.util.web.RbacConstant;
import com.lyd.utils.BaseMap;
import com.lyd.utils.Json;
import com.lyd.utils.ValidatorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户访问资源管理API")
@Controller
@RequestMapping(value = "/sys/res_do/")
public class RescourcesController {

	@Autowired
	ResourcesService resourcesService;
	
	@ApiOperation(value = "菜单管理首页", notes = "需要授权才可有访问的页面", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(ModelMap map){
		return "/system/resources/index";
	}
	
	@ApiOperation(value = "资源页面数据列表", notes = "需要授权才可以访问的页面", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public List<SysResourcesVO> list(@ModelAttribute ResourcesQuery qvo) throws Exception{
		List<SysResourcesVO> list = resourcesService.list(qvo);
		return resourcesService.list(qvo);
	}
	
	@ApiOperation(value = "创建资源", notes = "需要授权才可以访问的页面", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	public Json create(@RequestBody SysResourcesVO res){
		Json json = new Json();
		json.setSuccess(true);
		try {
			if(RbacConstant.RESOURCE_MENU.equals(res.getType())){
				res.setResKey(res.getName());
				res.setParentId(RbacConstant.RESOURCE_DEFAULT_PARENTID);
				res.setResUrl(res.getName());
			}
			ValidatorUtil.validateEntity(res);
			resourcesService.save(res);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "菜单树加载", notes = "加载菜单树", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "resList.do", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> select(@ModelAttribute ResourcesQuery qvo) throws Exception{
		/*//这样也可以
		Map<String, Object> maps = new HashMap<String, Object>();
		List<SysResourcesVO> list = resourcesService.list(qvo);
		maps.put("menuList", list);
		return maps;*/
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuList", resourcesService.list(qvo));
		ResponseEntity<Map<String, Object>> map = new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		return map;
	}
	
	@ApiOperation(value = "获取单个资源", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(value = "授权资源ID", required = true, dataType = "java.lang.String")
	@ResponseBody
	@RequestMapping(value = "get.do", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> get(@RequestParam(name = "id", required = true) String id) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		SysResourcesVO res = resourcesService.get(id);
		result.put("menu", res);
		ResponseEntity<Map<String, Object>> bean = new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		return bean;
	}
	
	@ApiOperation(value = "更新资源", notes = "需要授权才可以访问",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public Json update(@RequestBody SysResourcesVO res){
		Json json = new Json();
		json.setSuccess(true);
		try {
			if(RbacConstant.RESOURCE_MENU.equals(res.getType())){
				res.setResKey(res.getName());
				res.setParentId(RbacConstant.RESOURCE_DEFAULT_PARENTID);
				res.setResUrl(res.getName());
			} else {
				res.setResKey(res.getResUrl());
			}
			ValidatorUtil.validateEntity(res);
			BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
			BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
			updateMap.put("description", res.getDescription());
			updateMap.put("icon", res.getIcon());
			updateMap.put("ishide", res.getIsHide());
			updateMap.put("level", res.getLevel());
			updateMap.put("name", res.getName());
			updateMap.put("parentid", res.getParentId());
			updateMap.put("reskey", res.getResKey());
			updateMap.put("resurl", res.getResUrl());
			updateMap.put("type", res.getType());
			updateMap.put("colorid", res.getColorId());
			whereMap.put("id", res.getId());
			resourcesService.update(updateMap, whereMap);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "删除资源", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "id", value = "根据menuId删除资源", required = true, dataType = "java.lang.String")
	@ResponseBody
	@RequestMapping(value = "remove.do", method = RequestMethod.POST)
	public Json remove(@RequestParam(name = "id",required = true) String menuId){
		Json json = new Json();
		json.setSuccess(true);
		try {
			// String ids[] = ids2.split(",");
			// for (String id : ids) {
			resourcesService.removeById(menuId);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	
	
}
