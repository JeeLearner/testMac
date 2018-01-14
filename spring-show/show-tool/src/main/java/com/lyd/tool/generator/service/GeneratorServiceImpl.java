package com.lyd.tool.generator.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.tool.generator.dao.GeneratorDao;
import com.lyd.tool.generator.qvo.GeneratorQuery;
import com.lyd.tool.generator.utils.GenUtil;
import com.lyd.utils.Page;

@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService{

	@Autowired
	GeneratorDao generatorDao;
	
	public Page list(Page page, GeneratorQuery qvo) throws Exception {
		return generatorDao.list(page, qvo);
	}

	public Map<String, String> queryTable(String tableName) {
		return generatorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorDao.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (String tableName : tableNames) {
			//查询表信息
			Map<String, String> table = generatorDao.queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorDao.queryColumns(tableName);
			//生成代码
			try {
				GenUtil.generatorCode(table, columns, zip);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
