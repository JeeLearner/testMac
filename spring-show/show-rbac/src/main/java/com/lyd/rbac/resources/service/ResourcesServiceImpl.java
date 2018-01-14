package com.lyd.rbac.resources.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.lyd.rbac.resources.dao.ResourcesDao;
import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.qvo.ResourcesQuery;
import com.lyd.utils.BaseMap;

@Service
public class ResourcesServiceImpl implements ResourcesService {
	
	@Autowired
	ResourcesDao resourcesDao;
	
	@Cacheable(value = "resourcesCache", key = "#root.method.name+#root.args[0]")	
	public List<SysResourcesVO> list(ResourcesQuery qvo) throws Exception {
		return resourcesDao.list(qvo);
	}

	@Cacheable(value = "resroleCache", key = "#root.method.name+#root.args[0]")
	public List<SysResourcesVO> getRolesByRoleId(String roleId) throws Exception {
		return resourcesDao.getRolesByRoleId(roleId);
	}

	@Caching(evict = { 
			@CacheEvict(value = "resourcesCache", allEntries = true), 
			@CacheEvict(value = "resroleCache", allEntries = true) 
	})
	public SysResourcesVO save(SysResourcesVO res) {
		return resourcesDao.save(res);
	}

	@Cacheable(value = "resourcesCache", key = "#root.method.name+#root.args[0]")	
	public SysResourcesVO get(String id) throws Exception {
		ResourcesQuery qvo = new ResourcesQuery();
		qvo.setId(id);
		List<SysResourcesVO> list = resourcesDao.list(qvo);
		return list!=null && !list.isEmpty() ? list.get(0) : null;
	}

	@Caching(evict = { 
			@CacheEvict(value = "resourcesCache", allEntries = true), 
			@CacheEvict(value = "resroleCache", allEntries = true) 
	})
	public void update(BaseMap<String, Object> updateMap, BaseMap<String, Object> whereMap) {
		resourcesDao.update(updateMap, whereMap);
	}

	/** 根据ID删除 **/
	@Caching(evict = { 
			@CacheEvict(value = "resourcesCache", allEntries = true), 
			@CacheEvict(value = "resroleCache", allEntries = true) 
	})
	public void removeById(String menuId) {
		BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
		BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
		updateMap.put("isDelete", "1");
		whereMap.put("id", menuId);
		update(updateMap, whereMap);
	}
	
}
