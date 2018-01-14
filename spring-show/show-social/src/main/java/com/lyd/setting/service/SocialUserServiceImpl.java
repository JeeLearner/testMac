package com.lyd.setting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.setting.dao.SocialUserDao;
import com.lyd.setting.domain.SocialUserVO;
import com.lyd.setting.qvo.SocialUserQuery;
import com.lyd.utils.BaseMap;

@Service
public class SocialUserServiceImpl implements SocialUserService{

	@Autowired
	SocialUserDao socialUserDao;
	
	@Override
	public List<SocialUserVO> query(SocialUserQuery qvo) throws Exception {
		return socialUserDao.query(qvo);
	}
	
	@Override
	public void remove(BaseMap<String, Object> conditionmap) {
		socialUserDao.remove(conditionmap);
	}
}
