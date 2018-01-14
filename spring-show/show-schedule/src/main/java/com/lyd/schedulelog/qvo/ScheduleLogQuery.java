package com.lyd.schedulelog.qvo;

import com.lyd.jdbc.BaseQueryAble;
import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedulelog.domain.ScheduleLogDO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="任务记录查询条件",parent=ScheduleJobEntity.class, description="")
public class ScheduleLogQuery extends ScheduleLogDO implements BaseQueryAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298458537451387219L;

	@ApiModelProperty(name = "blurred", value = "是否模糊查询", dataType = "java.lang.Boolean")
	boolean	blurred; // 是否模糊查询
								
	public boolean isBlurred() {
		return blurred;
	}
	
	public void setBlurred(boolean blurred) {
		this.blurred = blurred;
	}
	
	@Override
	public String toString() {
		return "UserQvo [blurred=" + blurred + ", toString()=" + super.toString() + "]";
	}
}
