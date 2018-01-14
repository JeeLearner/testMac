package com.lyd.social.baidu.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.lyd.core.properties.BaiduProperties;
import com.lyd.social.baidu.conn.BaiduConnectionFactory;
import com.lyd.social.baidu.service.BaiduSocialSettingService;

@Configuration
public class BaiduAutoConfig extends SocialAutoConfigurerAdapter{

	@Autowired
	BaiduSocialSettingService baiduUserService;
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		Map<String, String> setting = baiduUserService.getSetting();
		BaiduProperties baiduConfig = new BaiduProperties();
		baiduConfig.setAppId(setting.get("client_ID"));
		baiduConfig.setAppSecret(setting.get("client_SERCRET"));
		return new BaiduConnectionFactory(baiduConfig.getProviderId(), baiduConfig.getAppId(), baiduConfig.getAppSecret());
	}
}
