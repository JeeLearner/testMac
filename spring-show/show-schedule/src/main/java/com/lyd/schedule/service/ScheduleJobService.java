package com.lyd.schedule.service;

import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedule.qvo.ScheduleJobQuery;
import com.lyd.utils.Page;

public interface ScheduleJobService {

	/* 定时任务列表 **/
	Page queryList(Page page, ScheduleJobQuery qvo) throws Exception;
	
	/* 新增任务 **/
	void save(ScheduleJobEntity scheduleJob) throws Exception;
	
	/* 根据jobId查询任务信息 **/
	ScheduleJobEntity get(Long jobId);
	
	/* 修改任务 **/
	void update(ScheduleJobEntity scheduleJob);
	
	/* 删除任务 **/
	void deleteBatch(Long[] jobIds);
	
	/* 暂停任务 **/
	void start(Long... jobIds);
	
	/* 恢复任务 **/
	void resume(Long... jobIds);
	
	/* 恢复任务 **/
	void run(Long... jobIds);
}
