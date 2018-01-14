package com.lyd.social.baidu.conn;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.lyd.social.baidu.api.Baidu;
import com.lyd.social.baidu.api.BaiduImpl;


/**
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * 	     泛型指向接口 多利 的 不可以 @Compoent.
 * @author lyd
 * @date 2017年10月23日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class BaiduServiceProvider extends AbstractOAuth2ServiceProvider<Baidu>{

	/** 获取授权码的url */
	private static final String	URL_AUTHORIZE = "http://openapi.baidu.com/oauth/2.0/authorize";
	/** 获取accessToken的url */
	private static final String	URL_ACCESS_TOKEN = "https://openapi.baidu.com/oauth/2.0/token";

	private String appId;
	
	/*
	 * 必须实现 父类的构造函数
	 */
	public BaiduServiceProvider(String appId, String appSecret) {
		// 父类的的 接口，第一个是应用号，第二个是 应用 密钥 ，第三个是 导向第四部URL,第四个是申请 access_token 的URL
		super(new BaiduOauth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}

	/*
	 * 在百度登录页面输入信息登录后跳回到这里
	 * 
	 * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
	 */
	@Override
	public Baidu getApi(String accessToken) {
		return new BaiduImpl(accessToken, appId);
	}

}
