package com.ray.study.weixin.common.config;

import com.ray.study.weixin.common.http.HttpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 23:02
 * @desc : Rest Configuration.
 **/
@Configuration
public class RestConfiguration {

    private final RestTemplateBuilder builder;

	@Autowired
	public RestConfiguration(RestTemplateBuilder builder) {
		this.builder = builder;
	}

	@Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

	@Bean
	public HttpHelper httpHelper() {
		return new HttpHelper(restTemplate());
	}

}
