package com.lyd.social.baidu.conn;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.lyd.social.baidu.api.Baidu;

/**
 * 百度连接工厂
 * @author lyd
 * @date 2017年10月23日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class BaiduConnectionFactory extends OAuth2ConnectionFactory<Baidu>{

	public BaiduConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new BaiduServiceProvider(appId, appSecret), new BaiduAdapter());
	}

}
