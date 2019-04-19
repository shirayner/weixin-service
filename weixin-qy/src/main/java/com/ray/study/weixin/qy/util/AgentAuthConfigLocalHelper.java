package com.ray.study.weixin.qy.util;

import com.ray.study.weixin.qy.AgentAuthConfig;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/19
 */
public class AgentAuthConfigLocalHelper {


	private static final AgentAuthConfigLocalHelper AGENT_AUTH_CONFIG_LOCAL = new AgentAuthConfigLocalHelper();

	private static final ThreadLocal<AgentAuthConfig> CURRENT_LOCAL = new ThreadLocal<>();

	private AgentAuthConfigLocalHelper() {
	}

	public static AgentAuthConfigLocalHelper getInstance() {
		return AGENT_AUTH_CONFIG_LOCAL;
	}


	public void setAgentAuthConfig(final AgentAuthConfig weiXinQYAuthConfig) {
		CURRENT_LOCAL.set(weiXinQYAuthConfig);
	}

	public AgentAuthConfig getAgentAuthConfig() {
		return CURRENT_LOCAL.get();
	}

	public void removeAgentAuthConfig() {
		CURRENT_LOCAL.remove();
	}


}
