package com.lyd.tool.generator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.tool.generator.qvo.GeneratorQuery;
import com.lyd.tool.generator.service.GeneratorService;
import com.lyd.tool.generator.utils.xss.XssHttpServletRequestWrapper;
import com.lyd.utils.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 代码生成器
 * @author lyd
 * @date 2017年9月22日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@Api(tags = "代码生成器API")
@Controller
@RequestMapping(value = "/tool/generator_do/")
public class GeneratorController {

	@Autowired
	GeneratorService generatorService;
	
	@ApiOperation(value = "代码生成器首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/tool/generator/index";
	}
	
	@ApiOperation(value = "代码生成器列表", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pageNow", value = "当前页数", required = true, dataType = "Integer"), 
		@ApiImplicitParam(name = "qvo", value = "查询页数", required = false, dataType = "GeneratorQuery") 
	})
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute GeneratorQuery qvo, ModelMap map, 
			@RequestParam(name = "pageNow", required = false, defaultValue = "1") Integer pageNow) throws Exception{
		Page page = new Page();
		page.setCurPage(pageNow);
		page = generatorService.list(page, qvo);
		page.init();
		return page;
	}
	
	/**
	 * 生成代码
	 * @author lyd
	 * @date 2017年9月23日
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@ApiOperation(value = "生成代码", notes = "只能页面传输，参数是tables 用英文逗号分隔")
	@RequestMapping(value = "code.do", method = RequestMethod.GET)
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[] {};
		// 获取表名，不进行xss过滤
		HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
		String tables = orgRequest.getParameter("tables");
		tableNames = tables.split(",");
		byte[] data = generatorService.generatorCode(tableNames);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"gencode.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}
