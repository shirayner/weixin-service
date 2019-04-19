package com.ray.study.weixin.qy.vo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : shira
 * @date : 2018/4/18
 * @time : 12:56
 * @desc :  用于token 同步,以及响应的返回
 **/
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVO  extends BaseVO {
    private String accessToken;
    private String contactsAccessToken;
    private String jsApiTicket;
}
