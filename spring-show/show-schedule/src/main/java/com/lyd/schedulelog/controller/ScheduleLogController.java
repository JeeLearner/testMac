package com.lyd.schedulelog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.schedulelog.qvo.ScheduleLogQuery;
import com.lyd.schedulelog.service.ScheduleLogService;
import com.lyd.utils.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "任务记录API", tags = "任务记录API")
@Controller
@RequestMapping(value = "/schedule/scheduleLog_do/")
public class ScheduleLogController {

	@Autowired
	ScheduleLogService scheduleLogService;
	
	@ApiOperation(value = "任务记录首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/schedule/log/index";
	}
	

	@ApiOperation(value = "任务记录列表", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "pageNow", value = "当前页数", required = true, dataType = "java.lang.Integer")
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute ScheduleLogQuery qvo, ModelMap map,
			 @RequestParam(name = "pageNow", required = false, defaultValue = "1") Integer pageNow) throws Exception{
		qvo.setBlurred(true);
		if(qvo.getBeanName() != null){
			qvo.setBeanName(qvo.getBeanName().trim());
		}
		Page page = new Page();
		page.setCurPage(pageNow);
		page = scheduleLogService.queryList(page, qvo);
		page.init();
		return page;
	}
	
	
}
