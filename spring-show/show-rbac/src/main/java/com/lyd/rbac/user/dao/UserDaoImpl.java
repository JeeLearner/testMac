package com.lyd.rbac.user.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.rbac.user.domain.UserVO;
import com.lyd.rbac.user.qvo.UserQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;
import com.lyd.utils.DateUtil;
import com.lyd.utils.Page;
import com.lyd.utils.constant.UserStateConstant;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	private static String	QUERY_TABLE_NAME	= "sys_user user";
	private static String	QUERY_TABLE_COLUMN	= "user.id, user.username, user.phone, user.email, user.state, user.password, user.createtime, user.isdelete, user.roleid, user.credentialssalt";
	
	public UserVO login(String loginname) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(" FROM ").append(QUERY_TABLE_NAME);
		sql.append(" WHERE ").append("username='").append(loginname).append("'");
		sql.append(" OR ").append("password='").append(loginname).append("'");
		sql.append(" OR ").append("phone='").append(loginname).append("'");
		List<UserVO> list = getJdbcTemplate().query(sql.toString(), new RowMapper<UserVO>() {
			
			public UserVO mapRow(ResultSet rs, int index) throws SQLException {
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setCreateTime(rs.getString("createtime"));
				user.setEmail(rs.getString("email"));
				user.setIsDelete(rs.getInt("isdelete"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setState(rs.getString("state"));
				user.setUsername(rs.getString("username"));
				user.setRoleId(rs.getString("roleid"));
				user.setCredentialsSalt(rs.getString("credentialssalt"));
				return user;
			}
		});
		return list.size() == 0 ? null : list.get(0);
	}
	
	public UserVO save(UserVO user) throws Exception {
		BaseMap<String, Object> map = new BaseMap<String, Object>();
		map.put("username", user.getUsername());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		map.put("state", user.getState());
		map.put("password", user.getPassword());
		map.put("createtime", user.getCreateTime());
		map.put("roleid", user.getRoleId());
		map.put("credentialssalt", user.getCredentialsSalt());
		
		String id = null;
		id = baseCreate(map, "sys_user", "id");
		user.setId(id);
		return user;
	}
	
	public boolean checkIsExist(UserQuery qvo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",role.name rolename")
		    .append(" FROM ").append(QUERY_TABLE_NAME)
		    .append(" LEFT JOIN ").append("sys_role role")
		    .append(" ON ").append("user.roleid=role.id");
		sql.append(" WHERE ").append("1=1");
		boolean username = DataTypeUtil.checkString(qvo.getUsername());
		boolean email = DataTypeUtil.checkString(qvo.getEmail());
		if(!username && !email){
			return false;
		}
		boolean flag = true;
		if(username){
			StringBuilder sqlSub = new StringBuilder();
			sqlSub.append(sql.toString()).append(" and user.username='" + qvo.getUsername() +"'");
			List<UserVO> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<UserVO>(UserVO.class));
			flag = flag && DataTypeUtil.checkList(list);
			if(!flag){
				return flag;
			}
		}
		if(email){
			StringBuilder sqlSub = new StringBuilder();
			sqlSub.append(sql.toString()).append(" and user.email='" + qvo.getEmail() +"'");
			List<UserVO> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(UserVO.class));
			flag = flag && DataTypeUtil.checkList(list);
			if(!flag){
				return flag;
			}
		}
		return flag;
	}
	
	public List<UserVO> list(UserQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",role.`name` rolename").append(" FROM ").append(QUERY_TABLE_NAME).append(" LEFT ").append("JOIN ").append("sys_role role").append(" ON ").append("user.roleid=role.id");
		sql.append(getWhere(qvo));
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(UserVO.class));
	}
	
	public void remove(BaseMap<String, Object> wheremap) {
		baseremove(wheremap, "sys_user");
	}
	
	public void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap) {
		this.baseupdate(updatemap, wheremap, "sys_user");
	}

	public void removeExpired() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append("sys_user")
			.append(" WHERE ").append("createtime<'")
			.append(DateUtil.convertToStrwithformat(DateUtil.setDate(new Date(), 0, 0, 0, 0, 0, -3), "yyyy-MM-dd hh:mm:ss")).append("'");
		sqlappen(sql, "state", UserStateConstant.DIE);
		getJdbcTemplate().update(sql.toString());
		
	}

	public Page list(Page page, UserQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",role.name rolename")
			.append(" FROM ").append(QUERY_TABLE_NAME)
			.append(" LEFT JOIN ").append("sys_role role").append(" ON ").append("user.roleid=role.id");
		sql.append(getWhere(qvo));
		page.setTotals(queryForInt(sql));
		if(page.getTotals() > 0){
			page.setResult(getJdbcTemplate().query(page.getPagesql(sql), new BeanPropertyRowMapper<UserVO>(UserVO.class)));
		} else {
			page.setResult(new ArrayList<UserVO>());
		}
		return page;
	}
	
	@Override
	public UserVO loginByUserid(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_TABLE_COLUMN).append(",role.`name` rolename").append(" FROM ").append(QUERY_TABLE_NAME).append(" LEFT ").append(" JOIN ").append("sys_role role").append(" ON ").append("user.roleid=role.id");
		sql.append(" WHERE ").append(" user.Id='").append(userId).append("'");
		List<UserVO> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<UserVO>(UserVO.class));
		return list.size() == 0 ? null : list.get(0);
	}
	
	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		/*String sql="select userId from " + "sys_" + "UserConnection where providerId = "+key.getProviderId()+" and providerUserId = "+key.getProviderUserId();
		System.out.println(sql);*/
		List<String> localUserIds = getJdbcTemplate().queryForList("select userId from " + "sys_" + "UserConnection where providerId = ? and providerUserId = ?", String.class, key.getProviderId(), key.getProviderUserId());		
		return localUserIds;
	}
	
	
	/** 用于统计个数
	 * 		一般用于分页或者直接得出记录数 */
	public int queryForInt(StringBuilder sql){
		return getJdbcTemplate().queryForObject(new Page().getCountsql(sql), Integer.class);
	}
	
	private String getWhere(UserQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" WHERE ").append("1=1 ");
		if (DataTypeUtil.checkInteger(qvo.getIsDelete())) {
			sql.append(" AND ").append("user.isdelete=").append(qvo.getIsDelete());
		}
		else {
			sql.append(" AND ").append("user.isdelete=0");// 默认
		}
		sqlappen(sql, "user.state", qvo.getState(), qvo);
		sqlappen(sql, "user.id", qvo.getId());
		sqlappen(sql, "user.createtime", qvo.getCreateTime(), qvo);
		sqlappen(sql, "user.email", qvo.getEmail(), qvo);
		sqlappen(sql, "user.password", qvo.getPassword());
		sqlappen(sql, "user.phone", qvo.getPhone(), qvo);
		sqlappen(sql, "user.username", qvo.getUsername(), qvo);
		sqlappen(sql, "user.roleid", qvo.getRoleId());
		sqlappen(sql, "user.credentialssalt", qvo.getCredentialsSalt());
		return sql.toString();
	}

	
}
