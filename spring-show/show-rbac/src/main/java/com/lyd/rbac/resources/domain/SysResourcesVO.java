package com.lyd.rbac.resources.domain;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "菜单类实体VO", parent = ResourcesDO.class)
public class SysResourcesVO extends ResourcesDO {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7166979139317222396L;
	private String	colorClass;	// 颜色
	private String	parentName;	// 父目录名称
	
	public String getColorClass() {
		return colorClass;
	}
	public void setColorClass(String colorClass) {
		this.colorClass = colorClass;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
