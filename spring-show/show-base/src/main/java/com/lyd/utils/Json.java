package com.lyd.utils;

import java.io.Serializable;

/**
 * JSON模型
 * 		用于后台向前台返回的JSON对象
 * @author lyd
 * @date 2017年9月7日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class Json implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -881610845231036040L;

	private boolean success = false;
	private String msg = "";
	private Object obj = null;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
