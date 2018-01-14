package com.lyd.schedule.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

import com.lyd.exception.web.MyException;
import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedule.qvo.ScheduleJobQuery;
import com.lyd.schedule.service.ScheduleJobService;
import com.lyd.utils.Json;
import com.lyd.utils.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "任务管理API", tags = "任务管理API")
@Controller
@RequestMapping(value = "/schedule/schedule_do/")
public class ScheduleController {

	@Autowired
	ScheduleJobService scheduleJobService;
	
	@ApiOperation(value = "定时任务首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/schedule/job/index";
	}
	
	@ApiOperation(value = "定时任务列表", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute ScheduleJobQuery qvo, ModelMap map,
			 @RequestParam(name = "pageNow", required = false, defaultValue = "1") Integer pageNow) throws Exception{
		qvo.setBlurred(true);
		if(qvo.getBeanName() != null){
			qvo.setBeanName(qvo.getBeanName().trim());
		}
		if(qvo.getMethodName() != null){
			qvo.setMethodName(qvo.getMethodName().trim());
		}
		Page page = new Page();
		page.setCurPage(pageNow);
		page = scheduleJobService.queryList(page, qvo);
		page.init();
		return page;
	}
	
	@ApiOperation(value = "新增任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	public Json create(@RequestBody ScheduleJobEntity scheduleJob){
		Json json = new Json();
		try {
			verifyForm(scheduleJob);
			scheduleJobService.save(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			json.setSuccess(false);
			return json;
		}
		json.setMsg("操作成功");
		json.setSuccess(true);
		return json;
	}
	
	@ApiOperation(value = "根据jobId获取任务信息", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "get.do", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> get(Long jobId){
		Map<String, Object> result = new HashMap<String, Object>();
		ScheduleJobEntity schedule = scheduleJobService.get(jobId);
		result.put("schedule", schedule);
		ResponseEntity<Map<String, Object>> bean = new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		return bean;
	}
	
	@ApiOperation(value = "修改定时任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public Json update(@RequestBody ScheduleJobEntity scheduleJob) {
		Json json = new Json();
		try {
			// 数据校验
			verifyForm(scheduleJob);
			scheduleJobService.update(scheduleJob);
		} catch (Exception e) {
			json.setMsg("操作失败");
			json.setSuccess(false);
			return json;
		}
		json.setMsg("操作成功");
		json.setSuccess(true);
		return json;
	}
	
	@ApiOperation(value = "删除任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "deleteBatch.do", method = RequestMethod.POST)
	public Json deleteBatch(String jobIds) {
		Json json = new Json();
		json.setSuccess(true);
		try {
			Long[] longIds = Str2LongArray(jobIds);
			scheduleJobService.deleteBatch(longIds);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "开启任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "start.do", method = RequestMethod.POST)
	public Json pause(String jobIds) {
		Json json = new Json();
		json.setSuccess(true);
		try {
			Long[] longIds = Str2LongArray(jobIds);
			scheduleJobService.start(longIds);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "恢复任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "resume.do", method = RequestMethod.POST)
	public Json resume(String jobIds) {
		Json json = new Json();
		json.setSuccess(true);
		try {
			Long[] longIds = Str2LongArray(jobIds);
			scheduleJobService.resume(longIds);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	@ApiOperation(value = "立即执行任务", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "run.do", method = RequestMethod.POST)
	public Json run(String jobIds) {
		Json json = new Json();
		json.setSuccess(true);
		try {
			Long[] longIds = Str2LongArray(jobIds);
			scheduleJobService.run(longIds);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
			return json;
		}
		json.setMsg("操作成功");
		return json;
	}
	
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(ScheduleJobEntity scheduleJob){
		if(StringUtils.isBlank(scheduleJob.getBeanName())){
			throw new MyException("spring bean名称不能为空");
		}
		if (StringUtils.isBlank(scheduleJob.getMethodName())) {
			throw new MyException("方法名称不能为空");
		}
		if (StringUtils.isBlank(scheduleJob.getCronExpression())) {
			throw new MyException("cron表达式不能为空");
		}
	}
	
	private Long[] Str2LongArray(String jobIds){
		String[] ids = jobIds.trim().split(",");
		List<Long> longids = new ArrayList<>();
		for (String idstr : ids) {
			Long[] longid = new Long[1];
			try {
				longid[0] = Long.parseLong(idstr);
				longids.add(longid[0]);
			} catch (Exception e) {
				continue;
			}
			//scheduleJobService.deleteBatch(longid);
		}
		Long[] longIds = longids.toArray(new Long[longids.size()]);
		return longIds;
	}
}
