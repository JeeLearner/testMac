package com.lyd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 项目入口
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@EnableAdminServer
@EnableScheduling  //LoginController.java中定时清除过期未激活的用户
@EnableAutoConfiguration(exclude = { org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class })
@SpringBootApplication
@ComponentScan(basePackages = { "org.activiti.rest.diagram", "com.lyd", "cn", "org.mybatis" }) 
public class Application {
	
	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
