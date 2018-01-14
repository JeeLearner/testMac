package com.lyd.tool.qrcode.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lyd.tool.qrcode.util.MatrixToImageWriter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "二维码工具API")
@RequestMapping(value = "/tool/qrcode_do/")
@Controller
public class QrCodeController {

	@ApiOperation(value = "跳转到二维码管理页面", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/tool/qrcode/index";
	}
	
	@RequestMapping(value = "gen.do", method = RequestMethod.GET, produces = "image/jpeg;charset=UTF-8")
	public void gen(String url, HttpServletResponse response, Integer width, Integer height){
		try {
			int iWidth = (width == null ? 200 : width);
			int iHeight = (height == null ? 200 : height);
			MatrixToImageWriter.createRqCode(url, iWidth, iHeight, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
