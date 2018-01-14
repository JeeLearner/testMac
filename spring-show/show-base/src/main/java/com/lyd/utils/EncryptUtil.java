package com.lyd.utils;
import java.security.MessageDigest;

/**
 * 加密工具
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class EncryptUtil {
	
	private EncryptUtil() {
		// 禁止实例化
	}
	
	/**
	 * MD5加密
	 * @author lyd
	 * @date 2017年9月6日
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
