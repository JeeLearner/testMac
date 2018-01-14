package com.lyd.confignamage.sysconfig.service;

import com.lyd.confignamage.sysconfig.domain.SysConfigEntity;
import com.lyd.utils.Page;

/**
 * 系统配置service
 * @author lyd
 * @date 2017年10月17日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public interface SysConfigService {
	
	/* 根据key获取value的Object对象 **/
	public <T> T getConfigObject(String key, Class<T> clazz);
	
	/* 根据key获取配置的value值 **/
	public String getValue(String key, String defaultValue);
	
	/* 根据key，更新value **/
	public int updateValueByKey(String key, String value);
	
	
	//项目没用到的方法
	public void save(SysConfigEntity config) throws Exception;
	public void update(SysConfigEntity config);
	public void deleteBatch(Long[] ids);
	public SysConfigEntity queryObject(Long id);
	Page list(Page page,SysConfigEntity qvo) throws Exception;
}
