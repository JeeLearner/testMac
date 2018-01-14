package com.lyd.schedulelog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.schedulelog.dao.ScheduleLogDao;
import com.lyd.schedulelog.domain.ScheduleLogDO;
import com.lyd.schedulelog.qvo.ScheduleLogQuery;
import com.lyd.utils.Page;

@Service
public class ScheduleLogServiceImpl implements ScheduleLogService {

	@Autowired
	ScheduleLogDao scheduleLogDao;
	
	@Override
	public Page queryList(Page page, ScheduleLogQuery qvo) throws Exception {
		return scheduleLogDao.queryList(page, qvo);
	}

	@Override
	public void save(ScheduleLogDO log) throws Exception {
		scheduleLogDao.save(log);
	}

}
