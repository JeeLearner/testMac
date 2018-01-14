package com.lyd.rbac.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lyd.utils.constant.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="个人网站首页API")
@Controller
public class PageIndexController {

	/**
	 * 跳转到网站首页 --未完成
	 * @author lyd
	 * @date 2017年9月29日
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "跳转个人网站首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toIndex(ModelMap map){
		map.put("systemIcp", SystemConstant.getICP());
		map.put("systemName", SystemConstant.getSystemName());
		map.put("systemDomain", SystemConstant.getSystemDomain());
		return "/pageindex/index";
	}
}
