package com.ray.study.weixin.qy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/19
 */
@Getter
@Setter
@NoArgsConstructor
public class AgentAuthConfig {

	private int agentId;

	private String agentSecret;

	private String token;

	private String encodingAesKey;

	//网页授权登录校验
	private String state;
}
