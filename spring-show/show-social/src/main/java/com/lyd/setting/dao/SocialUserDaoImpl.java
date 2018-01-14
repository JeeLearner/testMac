package com.lyd.setting.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.setting.domain.SocialUserVO;
import com.lyd.setting.qvo.SocialUserQuery;
import com.lyd.utils.BaseMap;

@Repository
public class SocialUserDaoImpl extends BaseDao implements SocialUserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	private static final String	QUERY_CLOMN	= " t.userid,t.providerid,t.provideruserid,t.rank,t.displayname,t.profileurl,t.imageurl,t.accesstoken,t.secret,t.refreshtoken,t.expiretime ";
	private static final String	QUERY_TABLE	= " sys_userconnection t ";
	
	@Override
	public List<SocialUserVO> query(SocialUserQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(QUERY_CLOMN).append(" FROM ").append(QUERY_TABLE);
		sql.append(getWhere(qvo));
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<SocialUserVO>(SocialUserVO.class));
	}

	@Override
	public void remove(BaseMap<String, Object> conditionMap) {
		baseremove(conditionMap, "sys_userconnection");
	}
	
	private String getWhere(SocialUserQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" WHERE ").append(" 1=1 ");
		sqlappen(sql, "t.userid", qvo.getUserId());
		sqlappen(sql, "t.providerid", qvo.getProviderId());
		sqlappen(sql, "t.provideruserid", qvo.getProviderUserId());
		return sql.toString();
	}
}
