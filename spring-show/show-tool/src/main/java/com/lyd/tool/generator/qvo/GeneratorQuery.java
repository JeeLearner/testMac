package com.lyd.tool.generator.qvo;

import java.io.Serializable;

public class GeneratorQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6413620598383163663L;

	/** 数据库表名 */
	private String table_name;

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
}
