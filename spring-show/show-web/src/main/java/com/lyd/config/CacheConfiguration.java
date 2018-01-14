package com.lyd.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 缓存的配置
 * 		ehcache.xml 在 main模块 的src/resources文件夹中 <br>
 * 		@Configuration和@EnableCaching 这两个注释取消则 缓存配置失效 ，缓存的方法 将无作用 
 * @author lyd
 * @date 2017年9月14日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

	/**
	 * ehcache的主要管理器
	 * @author lyd
	 * @date 2017年9月14日
	 * @param bean
	 * @return
	 */
	@Bean(name = "cache")
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
		return new EhCacheCacheManager(bean.getObject());
	}
	
	/** 据shared与否的设置,
	 * 
	 * Spring分别通过CacheManager.create()
	 * 
	 * 或new CacheManager()方式来创建一个ehcache基地.
	 * 
	 * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}
	
}
