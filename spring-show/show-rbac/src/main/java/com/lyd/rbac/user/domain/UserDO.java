package com.lyd.rbac.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;

import com.lyd.rbac.user.excel.ExcelVOAttribute;

/**
 * 用戶实体类(数据库)
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@ApiModel(value = "用戶实体类DO", description = "")
public class UserDO implements Serializable {
	
	private static final long	serialVersionUID	= 8884558988237838150L;
	
	@ExcelVOAttribute(name = "用户编码", column = "A")
	@ApiModelProperty(name = "id", value = "用户编码", dataType = "java.lang.String")
	private String id;
	
	@ExcelVOAttribute(name = "用户名", column = "B", isExport = true)
	@ApiModelProperty(name = "username", value = "用户名", dataType = "java.lang.String")
	@NotBlank(message = "用户名不能为空")
	private String username;
	
	@ExcelVOAttribute(name = "密码", column = "C", prompt = "密码保密哦！", isExport = false)
	@ApiModelProperty(name = "password", value = "密码", dataType = "java.lang.String")
	@NotBlank(message = "密码不能为空")
	private String password;
	
	@ExcelVOAttribute(name = "电子邮箱", column = "D", isExport = true)
	@ApiModelProperty(name = "email", value = "电子邮箱", dataType = "java.lang.String")
	@NotBlank(message = "电子邮箱不能为空")
	private String email;
	
	@ExcelVOAttribute(name = "手机号码", column = "E", isExport = true)
	@ApiModelProperty(name = "phone", value = "手机号", dataType = "java.lang.String")
	private String phone;
	
	@ExcelVOAttribute(name = "状态", column = "F", isExport = true)
	@ApiModelProperty(name = "state", value = "用户状态", dataType = "java.lang.String")
	private String state;
	
	@ExcelVOAttribute(name = "创建时间", column = "G", isExport = true)
	@ApiModelProperty(name = "createTime", value = "创建时间", dataType = "java.lang.String")
	private String createTime;
	
	@ExcelVOAttribute(name = "角色编码", column = "H", isExport = true)
	@ApiModelProperty(name = "roleId", value = "角色编码", dataType = "java.lang.String")
	@NotBlank(message = "角色编码不能为空")
	private String roleId;
	
	
	@ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "java.lang.Integer")
	private Integer isDelete;
	
	@ApiModelProperty(name = "credentialsSalt", value = "加密盐", hidden = true, dataType = "java.lang.String")
	private String credentialsSalt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	@Override
	public String toString() {
		return "UserDO [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", phone=" + phone + ", state=" + state + ", createTime=" + createTime + ", isDelete=" + isDelete
				+ ", roleId=" + roleId + ", credentialsSalt=" + credentialsSalt + "]";
	}
	
}
