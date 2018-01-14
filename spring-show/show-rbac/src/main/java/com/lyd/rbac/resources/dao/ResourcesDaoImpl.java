package com.lyd.rbac.resources.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.rbac.resources.domain.SysResourcesVO;
import com.lyd.rbac.resources.qvo.ResourcesQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;

@Repository
public class ResourcesDaoImpl extends BaseDao implements ResourcesDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	private static String	QUERY_TABLE_NAME	= "sys_resources res";
	private static String	QUERY_TABLE_COLUMN	= " res.id,res.name,res.parentid,res.reskey,res.type,res.resurl,res.level,res.icon,res.ishide,res.description,res.colorid ";
	
	public List<SysResourcesVO> list(ResourcesQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",color.colorclass").append(" FROM ").append(QUERY_TABLE_NAME).append(",sys_color color");
		sql.append(getWhere(qvo));
		sql.append(" order by res.id");
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<SysResourcesVO>(SysResourcesVO.class));
	}
	
	public List<SysResourcesVO> getRolesByRoleId(String roleId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",sc.colorclass").append(" FROM ").append("sys_resources res,sys_res_role rr,sys_color sc").append(" WHERE ").append("res.id=rr.resId");
		sqlappen(sql, "rr.roleid", roleId);
		sql.append(" AND ").append("rr.state=0");
		sql.append(" AND ").append("res.isdelete=0");
		sql.append(" AND ").append("sc.id=res.colorid");
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<SysResourcesVO>(SysResourcesVO.class));
	}
	
	public SysResourcesVO save(SysResourcesVO res) {
		BaseMap<String, Object> createmap = new BaseMap<String, Object>();
		String id = null;
		createmap.put("name", res.getName());
		createmap.put("parentid", res.getParentId());
		createmap.put("reskey", res.getResKey());
		createmap.put("type", res.getType());
		createmap.put("resurl", res.getResUrl());
		createmap.put("level", res.getLevel());
		createmap.put("icon", res.getIcon());
		createmap.put("ishide", res.getIsHide());
		createmap.put("description", res.getDescription());
		createmap.put("colorid", res.getColorId());
		try {
			id = baseCreate(createmap, "sys_resources", "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setId(id);
		return res;
	}
	
	public void update(BaseMap<String, Object> updateMap, BaseMap<String, Object> whereMap) {
		this.baseupdate(updateMap, whereMap, "sys_resources");
	}
	
	private String getWhere(ResourcesQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" WHERE ").append("1=1");
		if (DataTypeUtil.checkInteger(qvo.getIsDelete())) {
			sql.append(" AND ").append("res.isdelete=").append(qvo.getIsDelete());
		}
		else {
			sql.append(" AND ").append("res.isdelete=0");// 默认
		}
		sql.append(" AND ").append("res.colorid=color.id");
		sqlappen(sql, "res.colorid", qvo.getColorId());
		sqlappen(sql, "res.id", qvo.getId());
		sqlappen(sql, "res.level", qvo.getLevel());
		sqlappen(sql, "res.description", qvo.getDescription(), qvo);
		sqlappen(sql, "res.icon", qvo.getIcon(), qvo);
		sqlappen(sql, "res.ishide", qvo.getIsHide(), qvo);
		sqlappen(sql, "res.name", qvo.getName(), qvo);
		sqlappen(sql, "res.reskey", qvo.getResKey(), qvo);
		sqlappen(sql, "res.resurl", qvo.getResUrl(), qvo);
		sqlappen(sql, "res.type", qvo.getType());
		return sql.toString();
	}

	
}
