package com.lyd.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.lyd.config.session.ImoocExpiredSessionStrategy;
import com.lyd.config.session.ImoocInvalidSessionStrategy;

/** * @author Deament
 * 
 * @date 2017/1/1 */
@Configuration
public class SessionConfiguration {
	
	@Bean
	public InvalidSessionStrategy invalidSessionStrategy() {
		return new ImoocInvalidSessionStrategy("/SessionInvalidUrl");
	}
	
	@Bean
	
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		return new ImoocExpiredSessionStrategy("/SessionInvalidUrl");
	}
}
