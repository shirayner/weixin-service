package com.ray.study.weixin.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : shira
 * @date : 2018/4/19
 * @time : 9:33
 * @desc : 权限验证配置类
 *       前台js 通过config接口注入权限验证配置
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsSdkConfigVO {

    private String appId;
    private long timestamp;
    private String nonceStr;
    private String signature;


}
