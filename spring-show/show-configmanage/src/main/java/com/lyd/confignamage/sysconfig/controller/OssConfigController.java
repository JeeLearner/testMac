package com.lyd.confignamage.sysconfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.confignamage.oss.CloudStorageConfig;
import com.lyd.confignamage.sysconfig.SysConfigConstant;
import com.lyd.confignamage.sysconfig.domain.SysConfigEntity;
import com.lyd.confignamage.sysconfig.service.SysConfigService;
import com.lyd.exception.web.R;
import com.lyd.utils.Page;
import com.lyd.utils.ValidatorUtil;

import io.swagger.annotations.ApiOperation;

//本项目未用到此类
//本项目未用到此类
//本项目未用到此类
//本项目未用到此类
public class OssConfigController {

	@Autowired
	private SysConfigService sysConfigService;
	private final static String KEY = SysConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

	@ApiOperation(value = "系统配置信息首页", notes = "", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = { "index.do" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String index(ModelMap map) {
		CloudStorageConfig bean = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
		map.put("config", bean);
		return "/system/ossconfig/oss_config";
	}

	@ApiOperation(value = "配置列表", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@RequestMapping(value = { "list.do" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Page list(@ModelAttribute SysConfigEntity qvo,
			@RequestParam(name = "pageNow", required = false, defaultValue = "0") Integer pageNow, ModelMap map)
			throws Exception {
		Page page = new Page();
		page.setCurPage(pageNow);
		page = sysConfigService.list(page, qvo);
		page.init();
		return page;
	}

	@ResponseBody
	/** 配置信息 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		SysConfigEntity config = sysConfigService.queryObject(id);
		return R.ok().put("config", config);
	}

	/** 保存配置 */
	@ResponseBody
	@RequestMapping("save.do")
	public R save(@RequestBody SysConfigEntity config) throws Exception {
		ValidatorUtil.validateEntity(config);
		sysConfigService.save(config);
		return R.ok();
	}

	/** 修改配置 */
	@ResponseBody
	@RequestMapping("update.do")
	public R update(@RequestBody SysConfigEntity config) throws Exception {
		ValidatorUtil.validateEntity(config);
		sysConfigService.update(config);
		return R.ok();
	}

	/** 删除配置 */
	@RequestMapping("delete.do")
	@ResponseBody
	public R delete(@RequestBody Long[] ids) {
		sysConfigService.deleteBatch(ids);
		return R.ok();
	}
}