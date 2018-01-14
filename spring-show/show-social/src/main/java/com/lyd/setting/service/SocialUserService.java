package com.lyd.setting.service;

import java.util.List;

import com.lyd.setting.domain.SocialUserVO;
import com.lyd.setting.qvo.SocialUserQuery;
import com.lyd.utils.BaseMap;

public interface SocialUserService {

	/** 查询第三方登录账户的绑定信息*/
	List<SocialUserVO> query(SocialUserQuery qvo) throws Exception;
	
	/** 接触第三方账号的绑定*/
	void remove(BaseMap<String, Object> conditionmap);
}
