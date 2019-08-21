package com.ray.study.weixin.qy.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ray.study.weixin.common.constant.WeiXinCons;
import com.ray.study.weixin.common.http.HttpHelper;
import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.common.service.AuthService;
import com.ray.study.weixin.common.vo.JsSdkConfigVO;
import com.ray.study.weixin.qy.AgentAuthConfig;
import com.ray.study.weixin.qy.cache.WeiXinTokenCacheHelper;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import com.ray.study.weixin.qy.util.AgentAuthConfigLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 23:07
 * @desc :
 **/
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

    @Autowired
	HttpHelper httpHelper;

    @Autowired
    private WeiXinTokenCacheHelper tokenCacheHelper;

    /** 1.获取access_token的接口地址,有效期为7200秒 */
    private static final String GET_ACCESS_TOKEN_URL= WeiXinCons.API_GATEWAY_QY+"/cgi-bin/gettoken?corpid=";

    /** 2.获取getJsApiTicket的接口地址,有效期为7200秒 */
    private static final String GET_JS_API_TICKET_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/get_jsapi_ticket?access_token=";




    /**
     *  1. 开放接口：获取accessToken
     * @return
     */
    @Override
    public String getAccessToken() {
		String state = AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig().getState();

        String accessToken ;

        //1.先从缓存中获取accessToken
        if (tokenCacheHelper.hasAccessToken(state) ) {
            log.debug("Redis has data");
            accessToken = tokenCacheHelper.getAccessToken(state);
        } else {
            log.debug("Redis don't has data");
            // 2.缓存没有，再调用服务接口来获取
			AgentAuthConfig agentAuthConfig = AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig();
            accessToken=doGetAccessToken(agentAuthConfig.getAgentSecret()).getString(WeiXinCons.KEY_ACCESS_TOKEN);
            log.debug("============accessToken:  "+accessToken);

            // 2.2 数据写入缓存
			tokenCacheHelper.addAccessToken(state, accessToken);
        }

        return accessToken;
    }


    /**
     *  1.2 调用微信官方服务，获取accessToken
     * @return
     */
    @Override
	public JSONObject doGetAccessToken(String agentSecret) {
        String url=GET_ACCESS_TOKEN_URL+weiXinAuthConfig.getCorpId()+"&corpsecret="+agentSecret;
        return httpHelper.get( url);
    }


    @Override
    public String getContactsAccessToken(){
        String contactsAccessToken ;

		if (tokenCacheHelper.hasContactsAccessToken()) {
            log.debug("Redis has data");
            contactsAccessToken = tokenCacheHelper.getContactsAccessToken();
        } else {
            log.info("Redis don't has data");
            contactsAccessToken=doGetContactsAccessToken().getString(WeiXinCons.KEY_ACCESS_TOKEN);

            log.debug("============contactsAccessToken:  "+contactsAccessToken);

			tokenCacheHelper.addContactsAccessToken(contactsAccessToken);
        }

        return contactsAccessToken;
    }

    @Override
	public JSONObject doGetContactsAccessToken(){
        String url=GET_ACCESS_TOKEN_URL+weiXinAuthConfig.getCorpId()+"&corpsecret="+weiXinAuthConfig.getContactsSecret();
        return httpHelper.get( url);
    }

    /**
     * 2.开放接口，获取JsApiTicket
     * @return
     */
    @Override
    public String getJsApiTicket() {
		String state = AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig().getState();


		String jsApiTicket;

        //1.先从缓存中获取accessToken
        if (tokenCacheHelper.hasJsApiTicket(state)) {
            log.debug("Redis has data");
            jsApiTicket = tokenCacheHelper.getJsApiTicket(state);
        } else {
            log.info("Redis don't has data");
            // 2.缓存没有，再调用服务接口来获取
            String accessToken=getAccessToken();
            jsApiTicket= doGetJsApiTicket( accessToken).getString(WeiXinCons.KEY_TICKET);

            log.debug("============jsApiTicket:  "+jsApiTicket);

            // 2.2 数据写入缓存
			tokenCacheHelper.addJsApiTicket(state, jsApiTicket);
        }

        return jsApiTicket;
    }

    /**
     * 2.2 调用微信官方服务，获取JsApiTicket
     * @param accessToken
     * @return
     */
    @Override
    public JSONObject doGetJsApiTicket(@NotNull String accessToken) {
        String url=GET_JS_API_TICKET_URL+accessToken;
        return httpHelper.get( url);
    }



    /**
     * 4. 开放接口：获取 权限校验配置 参数
     *  获取前端使用JSSDK时，需要的权限校验配置参数
     * @param url
     * @return
     */
    @Override
    public ServerResponse<JsSdkConfigVO> getJsSdkConfig( String  url){
        //1.准备好参与签名的字段
        //1.1 url

        //1.2 timestamp 必填，生成签名的时间戳
        long timestamp =System.currentTimeMillis() / 1000;

        //1.3 noncestr  必填，生成签名的随机串
        String nonceStr = UUID.randomUUID().toString();

        String jsApiTicket=null;
        String signature=null;
        try {
            //1.4 jsapi_ticket 必填，生成签名的H5应用调用企业微信JS接口的临时票据
            String accessToken=getAccessToken();
            jsApiTicket=getJsApiTicket();

            //2.进行签名，获取signature
            signature=getJsSdkSignature(jsApiTicket,nonceStr,timestamp,url);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        log.debug("url:"+url);
        log.debug("jsApiTicket:"+jsApiTicket);


        //3.返回前端所需发票签名参数
        JsSdkConfigVO jsSdkConfigVO=new JsSdkConfigVO();
		jsSdkConfigVO.setAppId(weiXinAuthConfig.getCorpId());
		jsSdkConfigVO.setTimestamp(timestamp);
		jsSdkConfigVO.setNonceStr(nonceStr);
		jsSdkConfigVO.setSignature(signature);

        log.debug("JssdkConfigVO:"+jsSdkConfigVO.toString());

        return ServerResponse.createBySuccess(jsSdkConfigVO);
    }


    /**
     *  4.1 生成签名的函数
     *
     * @param jsTicket jsticket
     * @param nonceStr 随机串，自己定义
     * @param timestamp 生成签名用的时间戳
     * @param url 需要进行免登鉴权的页面地址，也就是执行dd.config的页面地址
     * @return
     * @throws Exception String
     */

    public static String getJsSdkSignature(String jsTicket, String nonceStr, long timestamp, String url) throws Exception {
        String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        log.debug("plainTex:"+plainTex);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(plainTex.getBytes("UTF-8"));
            return byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * @desc ：4.2 将bytes类型的数据转化为16进制类型
     *
     * @param hash
     * @return
     *   String
     */
    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", new Object[] { Byte.valueOf(b) });
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }



    /**
     *  同步token
     */
    @Override
    public void syncToken() {
		log.debug("Token Sync Job. Start！");

		List<AgentAuthConfig> agentAuthConfigList = weiXinAuthConfig.getAgent();

		for(AgentAuthConfig  agentAuthConfig : agentAuthConfigList){
			String state = agentAuthConfig.getState();
			String agentSecret = agentAuthConfig.getAgentSecret();
            cacheToken(state, agentSecret);
		}

		log.debug("Token Sync Job. End！");

    }

    private void cacheToken(String state, String agentSecret){
		// 1.调用服务获取 token
		String accessToken=doGetAccessToken(agentSecret).getString(WeiXinCons.KEY_ACCESS_TOKEN);
		String contactsAccessToken=doGetContactsAccessToken().getString(WeiXinCons.KEY_ACCESS_TOKEN);
		String jsApiTicket=doGetJsApiTicket(accessToken).getString(WeiXinCons.KEY_TICKET);

		tokenCacheHelper.addAccessToken(state, accessToken);
		tokenCacheHelper.addContactsAccessToken(contactsAccessToken);
		tokenCacheHelper.addJsApiTicket(state, jsApiTicket);

		log.debug("============accessToken============:{}",accessToken);
		log.debug("============contactsAccessToken====:{}",contactsAccessToken);
		log.debug("============jsApiTicket============:{}",jsApiTicket);
    }

}
