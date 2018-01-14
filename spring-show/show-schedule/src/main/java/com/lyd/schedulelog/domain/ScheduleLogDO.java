package com.lyd.schedulelog.domain;

import java.io.Serializable;

/**
 * 日志记录DO
 * @author lyd
 * @date 2017年10月15日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ScheduleLogDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5951955584387229893L;

	/** 日志id */
	private Long logId;
	/** 任务id */
	private Long jobId;
	/** spring bean名称 */
	private String beanName;
	/** 方法名 */
	private String methodName;
	/** 参数 */
	private String params;
	/** 任务状态 0：成功     1：失败 */
	private Integer status;
	/** 失败信息 */
	private String error;
	/** 耗时(单位：毫秒) */
	private Integer times;
	/** 创建时间 */
	private String createTime;
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
