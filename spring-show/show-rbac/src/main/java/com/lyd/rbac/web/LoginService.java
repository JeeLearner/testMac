package com.lyd.rbac.web;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.service.ResourcesService;
import com.lyd.rbac.user.dao.UserDao;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.qvo.UserQuery;
import com.lyd.utils.DataTypeUtil;

@Service
public class LoginService implements UserDetailsService {
	
	@Autowired
	UserDao				userdao;
	@Autowired
	ResourcesService	resourcesDao;
	
	/**
	 * 通过用户名查询用户信息并对相关resources进行授权
	 * @author lyd
	 * @date 2017年9月6日
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserQuery qvo = new UserQuery();
		qvo.setUsername(username);
		if (!DataTypeUtil.checkString(username)) {
			return null;
		}
		List<UserVO> list = null;
		try {
			list = userdao.list(qvo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (list == null || list.size() == 0) {
			throw new UsernameNotFoundException(username + " not found");
		}
		UserVO user = list.get(0);
		// 这里要把权限加进去 不然无法加载权限
		List<SysResourcesVO> authlist = null;
		try {
			authlist = resourcesDao.getRolesByRoleId(user.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
		for (SysResourcesVO s : authlist) {
			auths.add(new SimpleGrantedAuthority(s.getResUrl()));
		}
		user.setAuthorities(auths);
		return user;
	}
	
	/**
	 * 通过用户名查询用户
	 * @author lyd
	 * @date 2017年9月6日
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public UserVO getUserByUsername(String username) throws Exception {
		UserQuery qvo = new UserQuery();
		qvo.setUsername(username);
		List<UserVO> list = userdao.list(qvo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
