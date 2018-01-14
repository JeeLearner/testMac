package com.lyd.rbac.role.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.rbac.role.domain.RoleResDO;
import com.lyd.rbac.role.domain.SysRoleVO;
import com.lyd.rbac.role.qvo.RoleQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;
import com.lyd.utils.Page;

@Repository
public class RoleDaoImpl extends BaseDao implements RoleDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	private static String	QUERY_TABLE_NAME	= "sys_role role";
	private static String	QUERY_TABLE_COLUMN	= "role.id,role.state,role.name,role.rolekey,role.description ,role.isdelete";
	
	public Page list(Page page, RoleQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN)
			.append(" FROM ").append(QUERY_TABLE_NAME);
		sql.append(getWhere(qvo));
		page.setTotals(queryForInt(sql));
		if(page.getTotals() > 0){
			page.setResult(getJdbcTemplate().query(page.getPagesql(sql), new BeanPropertyRowMapper<SysRoleVO>(SysRoleVO.class)));
		} else {
			page.setResult(new ArrayList<SysRoleVO>());
		}
		return page;
	}
	
	public List<SysRoleVO> list(RoleQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN)
			.append(" FROM ").append(QUERY_TABLE_NAME);
		sql.append(getWhere(qvo));
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<SysRoleVO>(SysRoleVO.class));
	}
	
	public void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap) {
		this.baseupdate(updatemap, wheremap, "sys_role");
	}
	

	public void saveOrUpdateRole_Res(final List<RoleResDO> list) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append("sys_res_role(resid,roleid,state)")
			.append(" VALUE ").append("(?,?,?)")
			.append(" ON ").append("DUPLICATE ").append("KEY ")
			.append("UPDATE ").append("resid=?,roleid=?,state=?");
		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int count = 1;
				ps.setString(count++, list.get(i).getResId());
				ps.setString(count++, list.get(i).getRoleId());
				ps.setString(count++, list.get(i).getState());
				ps.setString(count++, list.get(i).getResId());
				ps.setString(count++, list.get(i).getRoleId());
				ps.setString(count++, list.get(i).getState());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public SysRoleVO save(SysRoleVO role) throws Exception {
		BaseMap<String, Object> createMap = new BaseMap<String, Object>();
		createMap.put("name", role.getName());
		createMap.put("roleKey", role.getRoleKey());
		createMap.put("description", role.getDescription());
		createMap.put("state", role.getState());
		String id = null;
		id = baseCreate(createMap, "sys_role", "id");
		role.setId(id);
		return role;
		
	}

	
	private String getWhere(RoleQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" WHERE ").append("1=1 ");
		if (DataTypeUtil.checkInteger(qvo.getIsDelete())) {
			sql.append("AND").append(" role.isdelete=").append(qvo.getIsDelete());
		}
		else {
			sql.append("AND").append(" role.isdelete=0");// 默认
		}
		sqlappen(sql, "role.id", qvo.getId());
		sqlappen(sql, "role.state", qvo.getState(), qvo);
		sqlappen(sql, "role.rolekey", qvo.getRoleKey(), qvo);
		sqlappen(sql, "role.description", qvo.getDescription(), qvo);
		sqlappen(sql, "role.name", qvo.getName(), qvo);
		return sql.toString();
	}

}
