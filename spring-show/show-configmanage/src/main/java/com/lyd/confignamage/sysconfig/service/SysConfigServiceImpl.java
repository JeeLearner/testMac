package com.lyd.confignamage.sysconfig.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyd.confignamage.sysconfig.dao.SysConfigDao;
import com.lyd.confignamage.sysconfig.domain.SysConfigEntity;
import com.lyd.utils.Page;

@Service
public class SysConfigServiceImpl implements SysConfigService{

	@Autowired
	SysConfigDao sysConfigDao;

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return JSON.parseObject(value, clazz);
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new Exception("获取参数失败");
		}
		return null;
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}

	@Override
	public int updateValueByKey(String key, String value) {
		int index = sysConfigDao.updateValueByKey(key, value);
		return index;
	}

	
	
	@Override
	public void save(SysConfigEntity config) throws Exception {
		sysConfigDao.save(config);
	}

	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	@Override
	public Page list(Page page, SysConfigEntity qvo) throws Exception {
		return sysConfigDao.list(page, qvo);
	}
}
