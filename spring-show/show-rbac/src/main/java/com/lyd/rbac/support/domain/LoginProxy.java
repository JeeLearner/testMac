package com.lyd.rbac.support.domain;

import com.lyd.rbac.user.domain.UserVO;

/**
 * 登录代理实体类
 * @author lyd
 * @date 2017年10月24日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class LoginProxy {

	String	redirectUrl	= "/common/index_do/index.do";
	String	loginname;
	String	password;
	String	result;
	boolean	success;
	/** 成功后的 用户**/
	UserVO	user;
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
	
}
