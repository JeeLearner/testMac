package com.lyd.util;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.lyd.exception.web.MyException;
import com.lyd.utils.web.SpringContextUtil;

/**
 * 执行定时任务
 * @author lyd
 * @date 2017年10月15日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ScheduleRunnable implements Runnable{

	private Object target;
	private Method method;
	private String params;
	
	
	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		} catch (Exception e) {
			throw new MyException("执行定时任务失败", e);
		}
	}


	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtil.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}
	
	

	
}
