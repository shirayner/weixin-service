package com.ray.study.weixin.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

/**
 * @author : shira
 * @date : 2018/4/20
 * @time : 13:34
 * @desc :
 **/

@Slf4j
public class HttpHelper {

    RestTemplate restTemplate;

	public HttpHelper(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public  JSONObject get(@NotNull String url){
        log.debug("================GET请求URL：{}",url);
        ResponseEntity<JSONObject> respString = restTemplate.getForEntity(url, JSONObject.class);
        return isHttpSuccess(respString);
    }


    public  JSONObject post(@NotNull String url, Object data){
        log.debug("================POST请求URL：{}",url);
        log.debug("================POST请求数据：{}",JSON.toJSONString(data));
        ResponseEntity<JSONObject> respString = restTemplate.postForEntity(url, data, JSONObject.class);
        return isHttpSuccess(respString);
    }




    private JSONObject isHttpSuccess(ResponseEntity<JSONObject> respString){
        JSONObject strBody=respString.getBody();
        if (respString.getStatusCodeValue() == 200) {
            log.debug("================网络请求成功:{}",strBody.toJSONString());

        }else{
            log.error("================网络请求失败:{}",strBody.toJSONString());
        }

        return strBody;
    }

    public Boolean isWXSuccess(JSONObject jsonObject){
        int errcode=jsonObject.getInteger("errcode");
        if(errcode == 0){
            return true;
        }else{
            return false;
        }
    }


}
