package com.lyd.rbac.role.domain;

import java.util.List;

public class SysRoleVO extends RoleDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4064280826184838173L;

	//角色权限列表
	private List<String> menuIdList;

	public List<String> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIdList = menuIdList;
	}
}
