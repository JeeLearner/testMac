package com.lyd.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.lyd.jdbc.DataBaseConstant;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {

	@Qualifier(DataBaseConstant.DB_QUARTZ)
	@Autowired
	DataSource dataSource;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(){
		Properties quartzProperties = new Properties();
		quartzProperties.setProperty("org.quartz.scheduler.instanceName", "lydScheduler");
		quartzProperties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
		quartzProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		quartzProperties.setProperty("org.quartz.threadPool.threadCount", "20");
		quartzProperties.setProperty("org.quartz.threadPool.threadPriority", "5");
		quartzProperties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX<");
		quartzProperties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "15000");
		quartzProperties.setProperty("org.quartz.jobStore.isClustered", "false");
		quartzProperties.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
		quartzProperties.setProperty("org.quartz.jobStore.misfireThreshold", "12000");
		quartzProperties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
		
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setQuartzProperties(quartzProperties);
		bean.setSchedulerName("lydScheduler");
		bean.setApplicationContextSchedulerContextKey("applicationContextKey");
		bean.setOverwriteExistingJobs(true);
		bean.setAutoStartup(true);
		bean.setStartupDelay(30);
		return bean;
	}
}
