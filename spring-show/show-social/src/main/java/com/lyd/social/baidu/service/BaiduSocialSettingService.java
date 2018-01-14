package com.lyd.social.baidu.service;

import java.util.Map;

public interface BaiduSocialSettingService {

	/** 获取百度登录配置信息*/
	Map<String, String> getSetting();
	
	/** 修改百度登录配置信息*/
	void updateSetting(String appid, String value, String url);
}
