package com.lyd.social.baidu.conn;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.lyd.social.baidu.api.Baidu;
import com.lyd.social.baidu.api.BaiduUserInfo;

/**
 * social的标准模型
 * 	   微信 api适配器，将微信 api的数据模型转为spring 。
 * @author lyd
 * @date 2017年10月23日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class BaiduAdapter implements ApiAdapter<Baidu>{

	private String uid;
	
	public BaiduAdapter() {
	}

	public BaiduAdapter(String uid) {
		this.uid = uid;
	}

	@Override
	public boolean test(Baidu api) {
		return true;
	}

	@Override
	public void setConnectionValues(Baidu api, ConnectionValues values) {
		BaiduUserInfo userInfo = api.getUserInfo(uid);
		//昵称
		values.setDisplayName(userInfo.getUsername());
		// 用户头像
		values.setImageUrl(userInfo.getPortrait());
		// 个人主页，不一定有。
		values.setProfileUrl(null);
		// 服务商的用户ID ,QQ 的是QQid // 这个异常需要捕获处理 处理成运行时异常
		values.setProviderUserId(userInfo.getUserid());
	}

	@Override
	public UserProfile fetchUserProfile(Baidu api) {
		BaiduUserInfo userInfo = api.getUserInfo(uid);
		UserProfile bean = new UserProfile(userInfo.getUserid(), userInfo.getUsername(), null, null, null, userInfo.getUsername());
		return bean;
	}

	@Override
	public void updateStatus(Baidu api, String message) {
		// do nothing
	}

}
