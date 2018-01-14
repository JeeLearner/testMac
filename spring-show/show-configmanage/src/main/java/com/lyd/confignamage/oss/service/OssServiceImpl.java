package com.lyd.confignamage.oss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.confignamage.oss.dao.OssDao;
import com.lyd.confignamage.oss.domain.OssEntity;
import com.lyd.utils.Page;

@Service
public class OssServiceImpl implements OssService {

	@Autowired
	OssDao ossDao;
	
	@Override
	public Page list(Page page, OssEntity qvo) {
		return ossDao.list(page, qvo);
	}

	@Override
	public void save(OssEntity oss) throws Exception {
		ossDao.save(oss);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		ossDao.deleteBatch(ids);
	}

	@Override
	public OssEntity queryObject(Long id) {
		return ossDao.queryObject(id);
	}

	@Override
	public void update(OssEntity sysOss) {
		ossDao.update(sysOss);
	}

	@Override
	public void delete(Long id) {
		ossDao.delete(id);
	}

}
