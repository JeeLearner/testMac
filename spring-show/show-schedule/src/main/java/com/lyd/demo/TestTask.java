package com.lyd.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务        演示Demo
 * @author lyd
 * @date 2017年10月15日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@Component("testTask")
public class TestTask {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// @Autowired
	// private UserService userService;
	
	public void test(String params) {

		// SysUserEntity user = sysUserService.queryObject(1L);
		// System.out.println(ToStringBuilder.reflectionToString(user));
		
		logger.info("带参数的test方法正在被执行，参数为：" + params);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void test2() {
		logger.info("不带参数的test2方法正在被执行...");
	}
}
