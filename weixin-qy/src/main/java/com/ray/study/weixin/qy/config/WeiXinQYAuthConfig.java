package com.ray.study.weixin.qy.config;

import com.ray.study.weixin.qy.AgentAuthConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/12
 */
@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "weixin.qy")
public class WeiXinQYAuthConfig {

	private String corpId;

	private String ContactsSecret;

	private List<AgentAuthConfig> agent;


}
