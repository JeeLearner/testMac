/**
 * 
 */
package com.lyd.core.support;

/**
 * 社交信息
 * @author lyd
 * @date 2017年10月23日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class SocialUserInfo {
	
	private String	providerId;
	private String	providerUserId;
	private String	nickname;
	private String	headimg;
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getProviderUserId() {
		return providerUserId;
	}
	
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getHeadimg() {
		return headimg;
	}
	
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
}
