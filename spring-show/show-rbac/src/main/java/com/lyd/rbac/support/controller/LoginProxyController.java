package com.lyd.rbac.support.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.lyd.rbac.support.domain.LoginProxy;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.service.UserService;
import com.lyd.utils.DesUtils;
import com.lyd.utils.web.SpringContextUtil;

/**
 * 登录代理Controller 登录逻辑 比如添加：重试失败次数、逻辑封IP...
 * 
 * @author lyd
 * @date 2017年9月11日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class LoginProxyController {

	public static LoginProxy proxyLogin(HttpServletRequest request, String username, String password,
			String redirectUrl) throws AuthenticationException, Exception {

		LoginProxy loginProxy = new LoginProxy();
		UserService userService = (UserService) SpringContextUtil.getBean(UserService.class);
		UserVO user = userService.login(username);
		if (user == null) {
			loginProxy.setResult("用户名或密码不正确！");
			return loginProxy;
		}

		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		AuthenticationManager authenticationManager = (AuthenticationManager) SpringContextUtil
				.getBean(AuthenticationManager.class);
		if(!(user.isAccountNonLocked())){
			loginProxy.setResult("用户已经被锁定不能绑定，请与管理员联系！");
			// map.put("error", "用户已经被锁定不能绑定，请与管理员联系！");
		}
		if (!user.isAccountNonExpired()) {
			loginProxy.setResult("账号未激活！");
			// map.put("error", "账号未激活！");
		}
		if (new DesUtils().encrypt(password).equals(user.getCredentialsSalt())) {
			UsernamePasswordAuthenticationToken token2 = new UsernamePasswordAuthenticationToken(user.getUsername(),
					new DesUtils().decrypt(user.getCredentialsSalt()));
			token2.setDetails(new WebAuthenticationDetails(request));
			Authentication authenticatedUser = authenticationManager.authenticate(token2);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			loginProxy.setSuccess(true);
			loginProxy.setUser(user);
			if (redirectUrl != null) {
				loginProxy.setRedirectUrl(redirectUrl);
			} else {
				loginProxy.setRedirectUrl("/common/index_do/index.do");
			}
		} else {
			// map.put("error", "用户或密码不正确！");
			loginProxy.setResult("用户名或密码不正确！");
		}
		return loginProxy;
	}
	

	public static LoginProxy loginByUserId(HttpServletRequest httpServletRequest, String userId, String redirurl) throws AuthenticationException, Exception {
		LoginProxy loginproxy = new LoginProxy();
		UserService userService = (UserService) SpringContextUtil.getBean(UserService.class);
		UserVO user = userService.get(userId);
		if (user == null) {
			loginproxy.setResult("用户不存在！");
			return loginproxy;
		}
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		AuthenticationManager authenticationManager = (AuthenticationManager) SpringContextUtil.getBean(AuthenticationManager.class);
		if (!(user.isAccountNonLocked())) {
			loginproxy.setResult("用户已经被锁定不能绑定，请与管理员联系！");
			// map.put("error", "用户已经被锁定不能绑定，请与管理员联系！");
		}
		if (!user.isAccountNonExpired()) {
			loginproxy.setResult("账号未激活！");
			// map.put("error", "账号未激活！");
		}
		UsernamePasswordAuthenticationToken token2 = new UsernamePasswordAuthenticationToken(user.getUsername(), new DesUtils().decrypt(user.getCredentialsSalt()));
		token2.setDetails(new WebAuthenticationDetails(httpServletRequest));
		Authentication authenticatedUser = authenticationManager.authenticate(token2);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		loginproxy.setSuccess(true);
		loginproxy.setUser(user);
		if (redirurl != null) {
			loginproxy.setRedirectUrl(redirurl);
		}
		else {
			loginproxy.setRedirectUrl("/common/index_do/index.do");
		}
		return loginproxy;
	}
}
