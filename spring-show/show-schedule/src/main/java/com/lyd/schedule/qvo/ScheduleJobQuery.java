package com.lyd.schedule.qvo;

import com.lyd.jdbc.BaseQueryAble;
import com.lyd.schedule.domain.ScheduleJobEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="定时任务查询条件",parent=ScheduleJobEntity.class, description="")
public class ScheduleJobQuery extends ScheduleJobEntity implements BaseQueryAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3408166307009524744L;

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
