package com.lyd.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lyd.utils.constant.SystemConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**在线API 接口。**/
@Configuration
@EnableSwagger2
/** http://localhost:8080/swagger-ui.html **/
public class Swagger2Configuration {
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}
	
	private ApiInfo apiInfo() {
		String comments="本系统以Spring为中心开发的SHOW个人平台，采用模块化开发，复用、组装性强。";
		Contact contact = new Contact(SystemConstant.getSystemAuth(), SystemConstant.getSystemDomain(), SystemConstant.getEmailAdress());
		return new ApiInfoBuilder()
					.title("SHOW个人平台 APIs")
					.description(comments)
					.termsOfServiceUrl(SystemConstant.getSystemDomain())
					.contact(contact)
					.version("1.0")
				.build();
	}
}