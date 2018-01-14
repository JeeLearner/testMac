package com.lyd.confignamage.oss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lyd.confignamage.oss.CloudStorageConfig;
import com.lyd.confignamage.oss.OssConstant;
import com.lyd.confignamage.oss.OssFactory;
import com.lyd.confignamage.oss.domain.OssEntity;
import com.lyd.confignamage.oss.service.OssService;
import com.lyd.confignamage.oss.validator.group.AliyunGroup;
import com.lyd.confignamage.oss.validator.group.QiniuGroup;
import com.lyd.confignamage.oss.validator.group.QcloudGroup;
import com.lyd.confignamage.sysconfig.SysConfigConstant;
import com.lyd.confignamage.sysconfig.service.SysConfigService;
import com.lyd.exception.web.MyException;
import com.lyd.exception.web.R;
import com.lyd.utils.DateUtil;
import com.lyd.utils.Json;
import com.lyd.utils.Page;
import com.lyd.utils.ValidatorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "OSS管理API", tags = "OSS管理API")
@Controller
@RequestMapping(value = "/configmanage/oss_do/")
public class OssController {

	private final static String	KEY	= SysConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	@Autowired
	OssService ossService;
	@Autowired
	SysConfigService sysConfigService;
	
	@ApiOperation(value = "OSS管理首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(){
		return "/configmanage/oss/oss";
	}
	
	@ApiOperation(value = "列表数据", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pageNow", value = "当前页数", required = true, dataType = "Integer"), 
		@ApiImplicitParam(name = "qvo", value = "查询条件", required = false, dataType = "SysOssEntity")
	})
	@ResponseBody
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	public Page list(@ModelAttribute OssEntity qvo, 
			@RequestParam(name = "pageNow", required = false, defaultValue = "1") Integer pageNow) {
		Page page = new Page();
		page.setCurPage(pageNow);
		page = ossService.list(page, qvo);
		page.init();
		return page;
	}
	
	@ApiOperation(value = "跳转到云存储配置页面", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "config.do", method = RequestMethod.GET)
	public R config() {
		CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
		return R.ok().put("config", config);
	}
	
	@ApiOperation(value = "保存云存储配置信息", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "saveConfig.do", method = RequestMethod.POST)
	public Json saveConfig(@RequestBody CloudStorageConfig config) throws Exception {
		Json json = new Json();
		try {
			//校验类型
			ValidatorUtil.validateEntity(config);
			if (config.getType() == OssConstant.CloudService.QINIU.getValue()) {
				// 校验七牛数据
				ValidatorUtil.validateEntity(config, QiniuGroup.class);
			}
			else if (config.getType() == OssConstant.CloudService.ALIYUN.getValue()) {
				// 校验阿里云数据
				ValidatorUtil.validateEntity(config, AliyunGroup.class);
			}
			else if (config.getType() == OssConstant.CloudService.QCLOUD.getValue()) {
				// 校验腾讯云数据
				ValidatorUtil.validateEntity(config, QcloudGroup.class);
			}
			int index = sysConfigService.updateValueByKey(KEY, JSON.toJSONString(config));
			if(index != 1){
				json.setMsg("操作失败");
				json.setSuccess(false);
				return json;
			}
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
	
	@ApiOperation(value = "上传文件", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "upload.do", method = RequestMethod.POST)
	public Json upload(@RequestParam("file") MultipartFile file) throws Exception {
		Json json = new Json();
		json.setSuccess(true);
		try {
			if (file.isEmpty()) {
				throw new MyException("上传文件不能为空");
			}
			Map<String, Object> result = new HashMap<String, Object>();
			// 上传文件
			String url = OssFactory.build().upload(file.getBytes());
			// 保存文件信息
			OssEntity ossEntity = new OssEntity();
			ossEntity.setUrl(url);
			ossEntity.setCreateDate(DateUtil.getDateTime());
			ossService.save(ossEntity);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("上传失败,请检查云存储配置！");
			return json;
		}
		json.setMsg("上传成功");
		return json;
	}
	
	/*@ApiOperation(value = "上传文件", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = { "upload.do" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new MyException("上传文件不能为空");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		// 上传文件
		String url = OssFactory.build().upload(file.getBytes());
		// 保存文件信息
		OssEntity ossEntity = new OssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(DateUtil.getDateTime());
		ossService.save(ossEntity);
		result.put("url", url);
		result.put("success", false);
		ResponseEntity<Map<String, Object>> bean = new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		return bean;
	}*/
	
	/** 删除 */
	@ApiOperation(value = "刪除文件（图片）", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	public Json delete(@RequestParam(name = "ids", required = true) String ids) {
		Json json = new Json();
		json.setSuccess(true);
		try {
			Long[] longIds = Str2LongArray(ids);
			ossService.deleteBatch(longIds);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败");
			return json;
		}
		json.setMsg("删除成功");
		return json;
	}
	
	
	private Long[] Str2LongArray(String strIds){
		String[] ids = strIds.trim().split(",");
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
