package com.lyd.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 百度属性配置
 * 	  前台登录入口路径
 * @author lyd
 * @date 2017年10月23日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class BaiduProperties extends SocialProperties{

	private String providerId = "baidu";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}
