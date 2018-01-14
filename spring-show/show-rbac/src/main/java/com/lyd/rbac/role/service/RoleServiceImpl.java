package com.lyd.rbac.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lyd.rbac.role.dao.RoleDao;
import com.lyd.rbac.role.domain.RoleResDO;
import com.lyd.rbac.role.domain.SysRoleVO;
import com.lyd.rbac.role.qvo.RoleQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.Page;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleDao roleDao;
	
	//@Cacheable(value = "roleCache", key = "#root.method.name+#root.args[0]+#root.method.name+#root.args[1]")
	public Page list(Page page, RoleQuery qvo) throws Exception {
		return roleDao.list(page, qvo);
	}

	@Cacheable(value = "roleCache", key = "#root.method.name+#root.args[0]")
	public List<SysRoleVO> list(RoleQuery qvo) throws Exception {
		return roleDao.list(qvo);
	}
	
	@Cacheable(value = "roleCache", key = "#root.method.name+#root.args[0]")
	public SysRoleVO get(String roleId) throws Exception {
		RoleQuery qvo = new RoleQuery();
		qvo.setId(roleId);
		List<SysRoleVO> list = roleDao.list(qvo);
		return list!=null && !list.isEmpty() ? list.get(0) : null;
	}

	@CacheEvict(value = "roleCache", allEntries = true)
	public void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap) {
		roleDao.update(updatemap, wheremap);
	}

	@CacheEvict(value = "resroleCache", allEntries = true)
	public void saveOrUpdateRole_Res(List<RoleResDO> list) {
		roleDao.saveOrUpdateRole_Res(list);
	}

	@CacheEvict(value = "roleCache", allEntries = true)
	public SysRoleVO save(SysRoleVO role) throws Exception {
		return roleDao.save(role);
	}

	@CacheEvict(value = "roleCache", allEntries = true)
	public void removeById(String roleId) {
		BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
		BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
		updateMap.put("isdelete", "1");
		whereMap.put("id", roleId);
		roleDao.update(updateMap, whereMap);
		
		
	}

}
