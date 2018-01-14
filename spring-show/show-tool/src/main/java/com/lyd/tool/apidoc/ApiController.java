package com.lyd.tool.apidoc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lyd.utils.constant.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "文档管理API")
@RequestMapping(value = "/tool/apidoc_do/")
@Controller
public class ApiController {

	@ApiOperation(value = "跳转到API文档查看页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("sysDomain",SystemConstant.getSystemDomain());
		return "/tool/apidoc/index";
	}
}
