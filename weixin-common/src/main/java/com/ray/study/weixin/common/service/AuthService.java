package com.ray.study.weixin.common.service;

import com.alibaba.fastjson.JSONObject;
import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.common.vo.JsSdkConfigVO;


public interface AuthService {

    String getAccessToken();

    String getContactsAccessToken();

    String getJsApiTicket();

    ServerResponse<JsSdkConfigVO> getJsSdkConfig( String url);

    JSONObject doGetAccessToken(String agentSecret);

    JSONObject doGetContactsAccessToken();

	JSONObject doGetJsApiTicket(String accessToken);

    void syncToken();
}
