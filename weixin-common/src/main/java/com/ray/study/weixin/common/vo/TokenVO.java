package com.ray.study.weixin.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : shira
 * @date : 2018/4/18
 * @time : 12:56
 * @desc :  用于token 同步,以及响应的返回
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVO {
    private String accessToken;
    private String contactsAccessToken;
    private String jsApiTicket;
}
