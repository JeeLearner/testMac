package com.lyd.rbac.role.domain;

import java.io.Serializable;

/**
 * 角色 -资源  关联实体（数据库）
 * @author lyd
 * @date 2017年9月20日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class RoleResDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5146988663490338501L;

	/** 角色编号 */
	private String roleId;
	/** 资源编号 */
	private String resId;
	/** 是否可用 */
	private String state;

	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "RoleResDO [roleId=" + roleId + ", resId=" + resId + ", state=" + state + "]";
	}
}
