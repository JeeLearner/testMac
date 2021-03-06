package com.lyd.confignamage.oss;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.lyd.exception.web.MyException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云存储
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class QiniuCloudStorageService extends CloudStorageService{

	private UploadManager uploadManager;
	private String token;
	
	public QiniuCloudStorageService(CloudStorageConfig config){
		this.config = config;
		// 初始化
		init();
	}
	
	private void init(){
		uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
		token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).uploadToken(config.getQiniuBucketName());
	}
	
	@Override
	public String upload(byte[] data, String path) {
		try {
			Response res = uploadManager.put(data, path, token);
			if(!res.isOK()){
				throw new RuntimeException("上传七牛出错：" + res.toString());
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MyException("上传文件失败，请核对七牛配置信息");
		}
		return config.getQiniuDomain() + "/" + path;
	}

	@Override
	public String upload(byte[] data) {
		return upload(data, getPath(config.getQiniuPrefix()));
	}

	@Override
	public String upload(InputStream inputStream, String path) {
		try {
			byte[] data = IOUtils.toByteArray(inputStream);
			return this.upload(data, path);
		} catch (IOException e) {
			throw new MyException("上传文件失败", e);
		}
	}

	@Override
	public String upload(InputStream inputStream) {
		return upload(inputStream, getPath(config.getQiniuPrefix()));
	}

}
