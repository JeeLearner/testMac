package com.lyd.confignamage.oss;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.lyd.exception.web.MyException;

/**
 * 阿里云存储
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class AliyunStorageService extends CloudStorageService{

	private OSSClient client;
	
	public AliyunStorageService(CloudStorageConfig config){
		this.config = config;
		// 初始化
		init();
	}
	
	private void init(){
		client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(), config.getAliyunAccessKeySecret());
	}
	
	@Override
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	@Override
	public String upload(byte[] data) {
		return upload(data, getPath(config.getAliyunPrefix()));
	}

	@Override
	public String upload(InputStream inputStream, String path) {
		try {
			client.putObject(config.getAliyunBucketName(), path, inputStream);
		} catch (Exception e) {
			 throw new MyException("上传文件失败，请检查配置信息", e);
		}
		return config.getAliyunDomain() + "/" + path;
	}

	@Override
	public String upload(InputStream inputStream) {
		return upload(inputStream, getPath(config.getAliyunPrefix()));
	}

}
