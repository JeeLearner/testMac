package com.lyd.confignamage.oss.dao;

import com.lyd.confignamage.oss.domain.OssEntity;
import com.lyd.utils.Page;

public interface OssDao {

	Page list(Page page, OssEntity qvo);

	void save(OssEntity oss) throws Exception;

	void deleteBatch(Long[] ids);

	// 项目没用到的方法
	OssEntity queryObject(Long id);
	void update(OssEntity sysOss);
	void delete(Long id);
}
