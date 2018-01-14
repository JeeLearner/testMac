package com.lyd.email;

import com.lyd.utils.constant.SystemConstant;

/**
 * 定义邮箱常量
 * 
 * @author lyd
 * @date 2017年9月8日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class EmailConstant {

	private EmailConstant() {
		// 禁止实例化
	}
	
	/** 使用的协议 **/
	public static final String PROTOCOL = "pop3";

	// 定义连接服务器的属性信息
	// 163
	public static final String POP163SERVER = "pop.163.com";
	public static final String SMTP163SERVER = "smtp.163.com";
	// qq
	public static final String POPqqSERVER = "pop.qq.com";
	public static final String SMTPqqSERVER = "smtp.qq.com";

	// 有SSL端口
	public static final String SSLPOP3PORT = "995";
	public static final String SSLSMTPPORT = "587";
	// 无SSL端口
	public static final String POP3PORT = "110";
	public static final String SMTPPORT = "25";
	
	/** 邮箱账号 **/
	public static String getEmailAccount(){
		return SystemConstant.getEmailAdress();
	}
	
	/** 邮箱密码 
	 * 		如果是QQ邮箱 请设置授权码（POP3）
	 */
	public static String getEmailPwd(){
		return SystemConstant.getEmailPwd();
	}
	
}
