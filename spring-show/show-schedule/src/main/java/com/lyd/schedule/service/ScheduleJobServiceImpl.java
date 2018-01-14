package com.lyd.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyd.schedule.dao.ScheduleJobDao;
import com.lyd.schedule.domain.ScheduleJobEntity;
import com.lyd.schedule.qvo.ScheduleJobQuery;
import com.lyd.util.ScheduleConstant.ScheduleStatus;
import com.lyd.util.ScheduleUtils;
import com.lyd.utils.DateUtil;
import com.lyd.utils.Page;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService{

	@Autowired
	ScheduleJobDao scheduleJobDao;
	
	@Autowired
	Scheduler		scheduler;
	
	/**
	 * 项目启动时，初始化定时器
	 * @author lyd
	 * @date 2017年10月14日
	 */
	@PostConstruct
	public void init() throws Exception {
		List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.queryList(new ScheduleJobQuery());
		for (ScheduleJobEntity scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if(cronTrigger == null){
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}
	
	@Override
	public Page queryList(Page page, ScheduleJobQuery qvo) throws Exception {
		return scheduleJobDao.queryList(page, qvo);
	}

	@Override
	public ScheduleJobEntity get(Long jobId) {
		return scheduleJobDao.get(jobId);
	}

	
	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) throws Exception {
		scheduleJob.setCreateTime(DateUtil.getDateTime());
		//设置默认状态
		scheduleJob.setStatus(ScheduleStatus.PAUSE.getValue());
		scheduleJobDao.save(scheduleJob);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
		scheduleJobDao.update(scheduleJob);
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}
		// 删除数据
		scheduleJobDao.deleteBatch(jobIds);
	}

	@Override
	@Transactional
	public void start(Long... jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.startJob(scheduler, jobId);
		}
		updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
	}
	
	@Override
	@Transactional
	public void resume(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		}
		updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
	}

	@Override
	@Transactional
	public void run(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.run(scheduler, get(jobId));
		}
	}
	

	public int updateBatch(Long[] jobIds, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jobIds);
		map.put("status", status);
		return scheduleJobDao.updateBatch(status, jobIds);
	}

}
