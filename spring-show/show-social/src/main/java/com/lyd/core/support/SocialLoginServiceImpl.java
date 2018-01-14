package com.lyd.core.support;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Repository;

import com.lyd.rbac.resources.dao.ResourcesDao;
import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.user.dao.UserDao;
import com.lyd.rbac.user.domain.UserVO;

/**
 * 向social导入用户  为用户增加权限
 * @author lyd
 * @date 2017年10月24日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@Repository
public class SocialLoginServiceImpl implements SocialUserDetailsService {
	
	@Autowired
	UserDao	userDao;
	@Autowired
	ResourcesDao resourcesDao;
	
	// 社交登陆接口
	/** (non-Javadoc)
	 * 
	 * @param userId
	 *            sys_userconnection 表的USERID */
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		return buildUser(userId);
	}
	
	private SocialUserDetails buildUser(String userId) {
		// 根据用户名查找用户信息
		// 根据查找到的用户信息判断用户是否被冻结
		UserVO user = userDao.loginByUserid(userId);
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
		// return new SocialUser(userId, password,
		// true, true, true, true,
		// AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		return new SocialUserDetails() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isEnabled() {
				return user.isEnabled();
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return user.isCredentialsNonExpired();
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return user.isCredentialsNonExpired();
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return user.isCredentialsNonExpired();
			}
			
			@Override
			public String getUsername() {
				return user.getUsername();
			}
			
			@Override
			public String getPassword() {
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return auths;
			}
			
			@Override
			public String getUserId() {
				return user.getId();
			}
		};
	}
}
