package com.lyd.rbac.role.qvo;

import com.lyd.jdbc.BaseQueryAble;
import com.lyd.rbac.role.domain.RoleDO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 角色查询条件 **/
@ApiModel(value = "角色查询条件", parent = RoleDO.class)
public class RoleQuery extends RoleDO implements BaseQueryAble {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1271122345528409177L;

	/** 是否模糊查询 **/
	@ApiModelProperty(name = "blurred", value = "是否模糊查询", dataType = "java.lang.Boolean", hidden = true)
	private boolean	blurred;
	
	public boolean isBlurred() {
		return blurred;
	}

	public void setBlurred(boolean blurred) {
		this.blurred = blurred;
	}

	@Override
	public String toString() {
		return "RoleQuery [blurred=" + blurred + "]";
	}

}
