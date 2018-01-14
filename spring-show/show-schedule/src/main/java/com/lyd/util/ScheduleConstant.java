package com.lyd.util;

/**
 * 定时任务常量
 * @author lyd
 * @date 2017年10月14日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ScheduleConstant {

	/**
	 * 定时任务状态
	 * @author lyd
	 * @date 2017年10月14日
	 * @version 1.0
	 * @CSDN http://blog.csdn.net/it_lyd
	 */
	public enum ScheduleStatus {
		
		/** 暂停 */
		PAUSE(0),
		/** 开启 */
		NORMAL(1);
		
		
		private int value;

		private ScheduleStatus(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
}
