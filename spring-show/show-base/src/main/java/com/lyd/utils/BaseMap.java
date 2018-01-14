package com.lyd.utils;
import java.util.LinkedHashMap;

/**
 * 改进的LinkedHashMap
 * 		put 元素取消 value为null的集合 
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class BaseMap<K, V> extends LinkedHashMap<K, V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 当value 为空时，不加入集合中 **/
	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		key = (K) key.toString().toLowerCase();
		if (key != null && value != null) {
			super.put(key, value);
		}
		return null;
	}
}
