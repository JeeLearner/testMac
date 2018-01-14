package com.lyd.confignamage.oss;

import com.lyd.confignamage.sysconfig.SysConfigConstant;
import com.lyd.confignamage.sysconfig.service.SysConfigService;
import com.lyd.utils.web.SpringContextUtil;

/**
 * OSS文件上传Factory
 * 
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public final class OssFactory {

	private static SysConfigService sysConfigService;
	static {
		OssFactory.sysConfigService = (SysConfigService) SpringContextUtil.getBean(SysConfigService.class);
	}

	public static CloudStorageService build() {
		// 获取云存储配置信息
		CloudStorageConfig config = sysConfigService.getConfigObject(SysConfigConstant.CLOUD_STORAGE_CONFIG_KEY,
				CloudStorageConfig.class);
		if (config.getType() == OssConstant.CloudService.QINIU.getValue()) {
			return new QiniuCloudStorageService(config);
		} else if (config.getType() == OssConstant.CloudService.ALIYUN.getValue()) {
			return new AliyunStorageService(config);
		} else if (config.getType() == OssConstant.CloudService.QCLOUD.getValue()) {
			return new QcloudStorageService(config);
		}
		return null;
	}
}
