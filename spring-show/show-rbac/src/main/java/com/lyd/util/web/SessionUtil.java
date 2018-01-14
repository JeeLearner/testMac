package com.lyd.util.web;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lyd.rbac.user.domain.UserVO;

public class SessionUtil {
	
	/** 获取登录账号的的对象
	 * 
	 * @author lanyuan Email：mmm333zzz520@163.com date：2014-2-27
	 * @param request
	 * @return UserVO  */
	public static UserVO findUserSession() {
		UserVO user = null;
		try {
			user = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		return user;
	}
}
