package com.lyd.social.baidu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.social.baidu.dao.BaiduSocialSettingDao;

@Service
public class BaiduSocialSettingServiceImpl implements BaiduSocialSettingService{

	@Autowired
	BaiduSocialSettingDao baiduUserDao;
	
	@Override
	public Map<String, String> getSetting() {
		return baiduUserDao.getSetting();
	}

	@Override
	public void updateSetting(String appid, String value, String url) {
		baiduUserDao.updateSetting(appid, value, url);
	}

}
