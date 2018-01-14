package com.lyd.rbac.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lyd.utils.constant.UserStateConstant;

@ApiModel(value = "用户类实体VO", parent = UserDO.class)
public class UserVO extends UserDO implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6514868907104830464L;
	
	@ApiModelProperty(name = "salt", value = "可解析加密盐", hidden = true, dataType = "java.lang.String")
	private String salt;
	
	@ApiModelProperty(name = "roleName", value = "角色名称", hidden = true, dataType = "java.lang.String")
	private String roleName;
	
	List<SimpleGrantedAuthority> auths;	// 用户自身权限
	public void setAuthorities(List<SimpleGrantedAuthority> auths) {
		this.auths = (List<SimpleGrantedAuthority>) auths;
	}
	
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	//implements method.
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// List<SimpleGrantedAuthority> auths = new ArrayList<>();
		// auths.add(new SimpleGrantedAuthority(getRoleid()));
		return auths;
	}
	
	public boolean isAccountNonExpired() {
		if (UserStateConstant.DIE.equals(getState())) {
			return false;
		}
		return true;
	}
	
	public boolean isAccountNonLocked() {
		if (UserStateConstant.LOCK.equals(getState())) {
			return false;
		}
		return true;
	}
	
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public boolean isEnabled() {
		return true;
	}
}
