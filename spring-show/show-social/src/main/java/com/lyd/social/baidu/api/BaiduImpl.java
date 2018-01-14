package com.lyd.social.baidu.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.TokenStrategy;

import com.alibaba.fastjson.JSONObject;

public class BaiduImpl extends AbstractOAuth2ApiBinding implements Baidu{

	private static final String	URL_GET_OPENID		= "https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser";
	private static final String	URL_GET_USERINFO	= "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
	
	private String appId;
	private String uid;
	
	public BaiduImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		this.uid = StringUtils.substringBetween(result, "\"uid\":\"", "\",");
	}

	@Override
	public BaiduUserInfo getUserInfo(String uid) {
		String result = getRestTemplate().getForObject(URL_GET_USERINFO, String.class, uid);
		try {
			BaiduUserInfo bean = JSONObject.parseObject(result, BaiduUserInfo.class);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取用户信息失败");
		}
	}
}
