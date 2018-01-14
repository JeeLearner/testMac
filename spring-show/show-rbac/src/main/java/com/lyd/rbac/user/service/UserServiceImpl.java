package com.lyd.rbac.user.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;

import com.lyd.rbac.user.dao.UserDao;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.qvo.UserQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.Page;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Cacheable(value = "userCache", key = "#root.method.name+#root.args[0]")
	public UserVO login(String loginname) {
		return userDao.login(loginname);
	}
	
	@CacheEvict(value = "userCache", allEntries = true)
	public UserVO save(UserVO user) throws Exception {
		return userDao.save(user);
	}

	@Cacheable(value = "userCache", key = "#root.method.name+#root.args[0]")
	public List<UserVO> list(UserQuery qvo) throws Exception {
		return userDao.list(qvo);
	}

	@CacheEvict(value = "userCache", allEntries = true)
	public void remove(BaseMap<String, Object> wheremap) {
		userDao.remove(wheremap);
	}

	@CacheEvict(value = "userCache", allEntries = true)
	public void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap) {
		userDao.update(updatemap, wheremap);
	}

	@Cacheable(value = "userCache", key = "#root.method.name+#root.args[0]")
	public UserVO get(String id) throws Exception {
		UserQuery userQvo = new UserQuery();
		userQvo.setId(id);
		List<UserVO> list = userDao.list(userQvo);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	@CacheEvict(value = "userCache", allEntries = true)
	public void removeExpired() throws Exception {
		userDao.removeExpired();
	}

	public boolean checkIsExist(UserQuery qvo) {
		return userDao.checkIsExist(qvo);
	}

	@Cacheable(value = "userCache", key = "#root.method.name+#root.args[0]+#root.method.name+#root.args[1]")
	public Page list(Page page, UserQuery qvo) throws Exception {
		return userDao.list(page, qvo);
	}

	@CacheEvict(value = "userCache", allEntries = true)
	public void removeById(String id) {
		BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
		BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
		updateMap.put("isdelete", "1");
		whereMap.put("id", id);
		userDao.update(updateMap, whereMap);
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		return userDao.findUserIdsWithConnection(connection);
	}
	
}
