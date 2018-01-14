package com.lyd.core.properties;

public class SocialProperties {

	private String filterProcessesUrl = "/auth";
	private BaiduProperties baidu = new BaiduProperties();
	
	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	public BaiduProperties getBaidu() {
		return baidu;
	}
	public void setBaidu(BaiduProperties baidu) {
		this.baidu = baidu;
	}
	
	
}
