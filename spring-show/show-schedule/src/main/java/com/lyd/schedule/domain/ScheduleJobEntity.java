package com.lyd.schedule.domain;

import java.io.Serializable;

/**
 * 定时任务实体-定时器
 * @author lyd
 * @date 2017年10月12日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ScheduleJobEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1241289289938061872L;

	/** 任务调度参数key */
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	/** 任务id */
	private Long jobId;
	/** spring bean名称 */
	private String beanName;
	/** 方法名 */
	private String methodName;
	/** 参数 */
	private String params;
	/** cron表达式 */
	private String cronExpression;
	/** 任务状态 */
	private Integer	status;	// 0暂停 1启用
	/** 备注 */
	private String remark;
	/** 创建时间 */
	private String createTime;
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
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
