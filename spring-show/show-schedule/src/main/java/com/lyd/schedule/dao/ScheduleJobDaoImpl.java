package com.lyd.schedule.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.jdbc.BaseDao;
import com.lyd.jdbc.DataBaseConstant;
import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedule.qvo.ScheduleJobQuery;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;
import com.lyd.utils.Page;

@Repository
public class ScheduleJobDaoImpl extends BaseDao implements ScheduleJobDao{

	@Qualifier(DataBaseConstant.JD_QUARTZ)
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Override
	public Page queryList(Page page, ScheduleJobQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select job_id,bean_name,method_name,params,cron_expression,status,remark,create_time from schedule_job job ");
		sql.append(" WHERE 1=1 ");
		sqlappen(sql, "bean_name", qvo.getBeanName(), qvo);
		sqlappen(sql, "method_name", qvo.getMethodName(), qvo);
		List<ScheduleJobEntity> list = getJdbcTemplate().query(page.getPagesql(sql), new BeanPropertyRowMapper<ScheduleJobEntity>(ScheduleJobEntity.class));
		page.setResult(list);
		page.setTotals(queryForInt(sql));
		return page;
	}
	
	@Override
	public List<ScheduleJobEntity> queryList(ScheduleJobQuery qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select job_id,bean_name,method_name,params,cron_expression,status,remark,create_time  from schedule_job job ");
		sql.append(" where 1=1 ");
		sqlappen(sql, "beanname", qvo.getBeanName());
		sql.append(" and status = 0 ");
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<ScheduleJobEntity>(ScheduleJobEntity.class));
		// return new ArrayList<ScheduleJobEntity>();
	}
	
	@Override
	public void save(ScheduleJobEntity scheduleJob) throws Exception {
		Map<String, Object> createMap = new LinkedHashMap<String, Object>();
		createMap.put("bean_name", scheduleJob.getBeanName());
		createMap.put("create_time", scheduleJob.getCreateTime());
		createMap.put("cron_expression", scheduleJob.getCronExpression());
		createMap.put("method_name", scheduleJob.getMethodName());
		createMap.put("params", scheduleJob.getParams());
		createMap.put("remark", scheduleJob.getRemark());
		createMap.put("status", scheduleJob.getStatus());
		Object id = basecreate(createMap, "schedule_job", true, new Long(0));
		scheduleJob.setJobId(new Long((long) id));
	}

	@Override
	public ScheduleJobEntity get(Long jobId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select job_id,bean_name,method_name,params,cron_expression,status,remark,create_time from schedule_job where job_id = " + jobId);
		List<ScheduleJobEntity> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<ScheduleJobEntity>(ScheduleJobEntity.class));
		return DataTypeUtil.checkList(list) ? list.get(0) : null;
	}

	@Override
	public void update(ScheduleJobEntity scheduleJob) {
		BaseMap<String, Object> updateMap = new BaseMap<String, Object>();
		BaseMap<String, Object> whereMap = new BaseMap<String, Object>();
		updateMap.put("bean_name", scheduleJob.getBeanName());
		updateMap.put("create_time", scheduleJob.getCreateTime());
		updateMap.put("cron_expression", scheduleJob.getCronExpression());
		updateMap.put("method_name", scheduleJob.getMethodName());
		updateMap.put("params", scheduleJob.getParams());
		updateMap.put("remark", scheduleJob.getRemark());
		updateMap.put("status", scheduleJob.getStatus());
		whereMap.put("job_id", scheduleJob.getJobId());
		baseupdate(updateMap, whereMap, "schedule_job");
	}

	@Override
	public void deleteBatch(Long[] jobIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from schedule_job where job_id =? ");
		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setLong(1, jobIds[index]);
			}
			
			@Override
			public int getBatchSize() {
				return jobIds.length;
			}
		});
	}

	
	
	@Override
	public int updateBatch(int status, Long[] job_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("update schedule_job set status = " + status + " where job_id =? ");
		int[] result = getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setLong(1, job_id[index]);
			}
			
			@Override
			public int getBatchSize() {
				return job_id.length;
			}
		});
		return 0;
	}


}
