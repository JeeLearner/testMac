package com.lyd.exception.web;

/**
 * 自定义异常
 * @author lyd
 * @date 2017年9月11日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class MyException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5864128665663188751L;

	private String msg;
	private int code = 500;
	
	public MyException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public MyException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public MyException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public MyException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	/** get/set */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
