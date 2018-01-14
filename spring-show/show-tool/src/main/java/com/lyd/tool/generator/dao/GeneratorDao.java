package com.lyd.tool.generator.dao;

import java.util.List;
import java.util.Map;

import com.lyd.tool.generator.qvo.GeneratorQuery;
import com.lyd.utils.Page;

public interface GeneratorDao {

	Page list(Page page, GeneratorQuery qvo) throws Exception;
	
	Map<String, String> queryTable(String tableName);

	List<Map<String, String>> queryColumns(String tableName);
}
