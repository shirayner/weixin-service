package com.ray.study.weixin.qy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/16
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ray.study.weixin.qy.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("企业微信服务接口列表")
				.description("SpringBoot版企业微信")
				.termsOfServiceUrl("https://blog.csdn.net/qq_26981333")
				.contact("shirayner")
				.version("1.0")
				.build();
	}
}
