package com.lyd.rbac.role.dao;

import java.util.List;

import com.lyd.rbac.role.domain.RoleResDO;
import com.lyd.rbac.role.domain.SysRoleVO;
import com.lyd.rbac.role.qvo.RoleQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.Page;

public interface RoleDao {

	/** 分页查询角色列表
	 * @throws Exception */
	Page list(Page page, RoleQuery qvo) throws Exception;
	
	/** 不分页查询角色列表 
	 * @throws Exception */
	List<SysRoleVO> list(RoleQuery qvo) throws Exception;
	
	/** 更新角色信息 */
	void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap);
	
	/** 角色授权 */
	void saveOrUpdateRole_Res(List<RoleResDO> list);
	
	/** 创建角色  返回主键 
	 * @return 
	 * @throws Exception */
	SysRoleVO save(SysRoleVO role) throws Exception;
}
