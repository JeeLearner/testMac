package com.lyd.rbac.user.dao;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;

import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.qvo.UserQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.Page;

@Component
public interface UserDao {
	
	/** 用户登录*/
	UserVO login(String loginname);
	
	/** 用户注册 */
	UserVO save(UserVO user) throws Exception;
	
	/** 检测用户是否已存在 */
	boolean checkIsExist(UserQuery qvo);
	
	/** 不分页查询 */
	List<UserVO> list(UserQuery qvo) throws Exception;
	
	/** 根据条件删除 **/
	void remove(BaseMap<String, Object> wheremap);
	
	/** 更新数据
	 * 		条件 和 需要更新的字段都不能为空 不限个数个条件
	 * @param updatemap
	 *            需要更新的字段和值
	 * @param wheremap
	 *            更新中的条件字段和值
	 **/
	// sys_user
	void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap);
	
	/** 清除过期没有激活的用户  */
	void removeExpired() throws Exception;
	
	/**
	 * 用户管理操作
	 */
	/** 分页查询用户列表*/
	Page list(Page page, UserQuery qvo) throws Exception;
	
	/** 根据userId登陆  */
	UserVO loginByUserid(String userId);
	
	/** 获取用户社交账号  */
	public List<String> findUserIdsWithConnection(Connection<?> connection);
}
