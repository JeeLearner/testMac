package com.lyd.rbac.role.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色实体类（数据库）
 * @author lyd
 * @date 2017年9月19日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@ApiModel(value = "用户实体类DO")
public class RoleDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3320599434097833664L;

	@ApiModelProperty(name = "id", value = "角色ID", dataType = "java.lang.String", hidden = false)
	private String id;
	
	@ApiModelProperty(name = "state", value = "角色状态  0：正常   1：禁用", dataType = "java.lang.String", hidden = false)
	@NotBlank(message = "角色状态不能为空")
	private String state;
	
	@ApiModelProperty(name = "name", value = "角色名称", dataType = "java.lang.String", hidden = false)
	@NotBlank(message = "角色名称不能为空")
	private String name;
	
	@ApiModelProperty(name = "roleKey", value = "角色唯一标识", dataType = "java.lang.String", hidden = false)
	@NotBlank(message = "角色KEY不能为空")
	private String roleKey;
	
	@ApiModelProperty(name = "description", value = "角色描述 ", dataType = "java.lang.String", hidden = false)
	private String description;
	
	@ApiModelProperty(name = "isdelete", value = "是否删除 ", dataType = "java.lang.Integer", hidden = true)
	private Integer isDelete;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "RoleDO [id=" + id + ", state=" + state + ", name=" + name + ", roleKey=" + roleKey + ", description="
				+ description + ", isDelete=" + isDelete + "]";
	}
}
