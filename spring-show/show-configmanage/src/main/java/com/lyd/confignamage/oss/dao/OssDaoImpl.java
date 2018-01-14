package com.lyd.confignamage.oss.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyd.confignamage.oss.domain.OssEntity;
import com.lyd.jdbc.BaseDao;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;
import com.lyd.utils.Page;

@Repository
public class OssDaoImpl extends BaseDao implements OssDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public Page list(Page page, OssEntity qvo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select id,url,create_date from sys_oss oss ");
		sql.append(" where 1=1 ");
		page.setTotals(queryForInt(sql));
		if (page.getTotals() > 0) {
			List<OssEntity> list = getJdbcTemplate().query(page.getPagesql(sql),
					new BeanPropertyRowMapper<OssEntity>(OssEntity.class));
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<OssEntity>());
		}
		return page;
	}

	@Override
	public void save(OssEntity oss) throws Exception {
		Map<String, Object> createMap = new BaseMap<String, Object>();
		createMap.put("url", oss.getUrl());
		createMap.put("create_date", oss.getCreateDate());
		basecreate(createMap, "sys_oss", false, null);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		for (Long id : ids) {
			if (id != null) {
				BaseMap<String, Object> conditionMap = new BaseMap<>();
				conditionMap.put("id", id);
				baseremove(conditionMap, "sys_oss");
			}
		}
	}

	@Override
	public OssEntity queryObject(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("	select id,url,create_date from sys_oss oss where id = " + id);
		List<OssEntity> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<OssEntity>(OssEntity.class));
		return DataTypeUtil.checkList(list) ? list.get(0) : null;
	}

	@Override
	public void update(OssEntity sysOss) {
		BaseMap<String, Object> wheremap = new BaseMap<>();
		BaseMap<String, Object> updatemap = new BaseMap<>();
		updatemap.put("url", sysOss.getUrl());
		updatemap.put("create_date", sysOss.getCreateDate());
		wheremap.put("id", sysOss.getId());
		baseupdate(updatemap, wheremap, "sys_oss");
	}

	@Override
	public void delete(Long id) {
		BaseMap<String, Object> conditionmap = new BaseMap<>();
		conditionmap.put("id", id);
		if (id != null) {
			baseremove(conditionmap, "sys_oss");
		}
	}

}
