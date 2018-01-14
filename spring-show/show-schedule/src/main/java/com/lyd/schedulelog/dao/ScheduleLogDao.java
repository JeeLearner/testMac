package com.lyd.schedulelog.dao;

import com.lyd.schedulelog.domain.ScheduleLogDO;
import com.lyd.schedulelog.qvo.ScheduleLogQuery;
import com.lyd.utils.Page;

public interface ScheduleLogDao {

	/* 任务记录列表**/
	Page queryList(Page page, ScheduleLogQuery qvo) throws Exception;
	
	/* 保存任务记录 **/
	void save(ScheduleLogDO log) throws Exception;
}
