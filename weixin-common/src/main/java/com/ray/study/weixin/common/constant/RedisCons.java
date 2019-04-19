package com.ray.study.weixin.common.constant;

/**
 * @author : shira
 * @date : 2018/4/18
 * @time : 13:08
 * @desc :
 **/
public class RedisCons {
	public static final String KEY_TOKEN_PREFIX="weixin:qy:token:";

    public static final String KEY_ACCESS_TOKEN="access_token";

    public static final String KEY_JS_API_TICKET="js_api_ticket";

    public static final String KEY_CARD_API_TICKET="card_api_ticket";

	public static final String KEY_CONTACTS_ACCESS_TOKEN="contacts_Access_Token";

	/** 缓存更新频率，单位秒 */
	public static final int TIME_OUT_TWO_HOUR=  7200;



}
