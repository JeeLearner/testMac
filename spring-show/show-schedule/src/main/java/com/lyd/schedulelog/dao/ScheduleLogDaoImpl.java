package com.lyd.schedulelog.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.jdbc.DataBaseConstant;
import com.lyd.schedulelog.domain.ScheduleLogDO;
import com.lyd.schedulelog.qvo.ScheduleLogQuery;
import com.lyd.utils.Page;

@Repository
public class ScheduleLogDaoImpl extends BaseDao implements ScheduleLogDao {

	@Autowired
	@Qualifier(DataBaseConstant.JD_QUARTZ)
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	
	@Override
	public Page queryList(Page page, ScheduleLogQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select log_id,job_id,bean_name,method_name,params,status,error,times,create_time from schedule_job_log  ");
		sql.append(" WHERE 1=1 ");
		sqlappen(sql, "bean_name", qvo.getBeanName(), qvo);
		sql.append(" ORDER BY create_time DESC");
		List<ScheduleLogDO> list = getJdbcTemplate().query(page.getPagesql(sql), new BeanPropertyRowMapper<ScheduleLogDO>(ScheduleLogDO.class));
		page.setResult(list);
		page.setTotals(queryForInt(sql));
		return page;
	}
	
	@Override
	public void save(ScheduleLogDO log) throws Exception {
		Map<String, Object> createmap = new LinkedHashMap<String, Object>();
		createmap.put("bean_name", log.getBeanName());
		createmap.put("create_time", log.getCreateTime());
		createmap.put("error", log.getError());
		createmap.put("job_id", log.getJobId());
		createmap.put("method_name", log.getMethodName());
		createmap.put("params", log.getParams());
		createmap.put("status", log.getStatus());
		createmap.put("times", log.getTimes());
		basecreate(createmap, "schedule_job_log", false, new Long(0));
	}
	


}
