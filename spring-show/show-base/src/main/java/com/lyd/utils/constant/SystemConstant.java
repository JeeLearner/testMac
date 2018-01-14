package com.lyd.utils.constant;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.lyd.config.SystemConfig;
import com.lyd.utils.web.SpringContextUtil;

/** 系统常量类 **/
public class SystemConstant {
	
	private SystemConstant() {
		// 禁止实例化
	}
	
	/** 是否调试模式 **/
	/*private static Properties props	= new Properties();
	
	public static final boolean	DEBUG	= false;
	static {
		try {
			props.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("systemconstant.properties"), "UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return props.getProperty(key);
	}*/
	
	static SystemConfig config = null;
	
	public static SystemConfig getConfig(){
		if(config == null){
			config = (SystemConfig) SpringContextUtil.getBean(SystemConfig.class);
		}
		return config;
	}
	
	
	
	/** 系统域名 www.???.com **/
	public static String getSystemDomain() {
		return getConfig().getDomain();
	}
	
	/** 系统名称 **/
	public static String getSystemName() {
		return getConfig().getName();
	}
	
	/** 系统配置邮箱 **/
	public static String getEmailAdress() {
		return getConfig().getEmail();
	}
	
	/** 系统配置邮箱密码 **/
	public static String getEmailPwd() {
		return getConfig().getEmailPwd();
	}
	
	/** 系统开发者 **/
	public static String getSystemAuth() {
		return getConfig().getAuth();
	}
	
	/** 系统备案号 **/
	public static String getICP() {
		return getConfig().getIcp();
	}
}
