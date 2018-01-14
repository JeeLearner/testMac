package com.lyd.inter;

/**
 * 组织接口
 * @author lyd
 * @date 2017年9月7日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public interface InterOrganization {

	/** 是否子节点*/
	boolean isLeaf();
	
	void add(InterOrganization org);
	
	void remove(InterOrganization org);
	
}
