package com.lyd.schedule.dao;

import java.util.List;

import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedule.qvo.ScheduleJobQuery;
import com.lyd.utils.Page;

public interface ScheduleJobDao {

	/* 定时任务列表 **/
	Page queryList(Page page, ScheduleJobQuery qvo) throws Exception;
	List<ScheduleJobEntity> queryList(ScheduleJobQuery qvo) throws Exception;
	
	/* 新增任务 **/
	void save(ScheduleJobEntity scheduleJob) throws Exception;
	
	/* 根据jobId查询任务信息 **/
	ScheduleJobEntity get(Long jobId);
	
	/* 修改任务 **/
	void update(ScheduleJobEntity scheduleJob);
	
	/* 删除任务 **/
	void deleteBatch(Long[] jobIds);
	
	
	
	/** 批量更新状态 */
	int updateBatch(int status, Long[] job_id);
}
