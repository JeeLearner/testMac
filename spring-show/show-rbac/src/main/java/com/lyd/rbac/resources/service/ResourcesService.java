package com.lyd.rbac.resources.service;
import java.util.List;

import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.qvo.ResourcesQuery;
import com.lyd.utils.BaseMap;

public interface ResourcesService {
	
	/** 不分页查询  */
	List<SysResourcesVO> list(ResourcesQuery qvo) throws Exception;
	
	/** 角色 权限集合  */
	List<SysResourcesVO> getRolesByRoleId(String roleId) throws Exception;
	
	/** 返回主键的创建 */
	SysResourcesVO save(SysResourcesVO res);
	
	/** 根据menuId查询menu */
	SysResourcesVO get(String id) throws Exception;
	
	/** 更新menu */
	void update(BaseMap<String, Object> updateMap, BaseMap<String, Object> whereMap);
	
	/** 根据ID删除menu */
	void removeById(String menuId);
}
