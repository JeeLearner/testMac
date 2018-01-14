package com.lyd.exception.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * 返回数据
 * @author lyd
 * @date 2017年9月11日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class R extends HashMap<String, Object> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -139216828522902240L;

	public R() {
		put("success", false);
		put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	/** error */
	public static R error(){
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}

	public static R error(String msg){
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
	}
	
	public static R error(int code, String msg){
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("success", false);
		return r;
	}
	
	/** ok */
	public static R ok(String msg) {
		R r = new R();
		r.put("success", true);
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.put("success", true);
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}
	
	
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
