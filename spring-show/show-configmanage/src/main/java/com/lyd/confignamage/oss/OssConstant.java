package com.lyd.confignamage.oss;

/**
 * OSS配置常量
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class OssConstant {

	public enum CloudService {
		/** 七牛云 */
		QINIU(1),
		/** 阿里云 */
		ALIYUN(2),
		/** 腾讯云 */
		QCLOUD(3);
		
		private int value;

		public int getValue() {
			return value;
		}
		
		private CloudService(int value) {
			this.value = value;
		}
	}
}
