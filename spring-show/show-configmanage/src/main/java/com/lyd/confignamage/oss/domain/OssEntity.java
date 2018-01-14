package com.lyd.confignamage.oss.domain;

import java.io.Serializable;

/**
 * OSS设置实体类
 * @author lyd
 * @date 2017年10月17日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class OssEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8884653514587944185L;

	private Long id;
	//URL地址
	private String url;
	//创建时间
	private String createDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
