package com.lyd.confignamage.sysconfig.dao;

import org.apache.ibatis.annotations.Param;

import com.lyd.confignamage.sysconfig.domain.SysConfigEntity;
import com.lyd.utils.Page;

/**
 * 系统配置dao
 * 
 * @author lyd
 * @date 2017年10月17日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public interface SysConfigDao {

	/* 根据key，查询value **/
	String queryByKey(String key);

	/* 根据key，更新value **/
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	// int updateValueByKey(String key, String value);

	
	// 项目没用到的方法
	public void save(SysConfigEntity config) throws Exception;
	public void update(SysConfigEntity config);
	public void deleteBatch(Long[] ids);
	public SysConfigEntity queryObject(Long id);
	Page list(Page page, SysConfigEntity qvo) throws Exception;
}
