package com.ray.study.weixin.qy.vo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : shira
 * @date : 2018/4/19
 * @time : 9:33
 * @desc : 权限验证配置类
 *       前台js 通过config接口注入权限验证配置
 **/
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsSdkConfigVO  extends BaseVO {

    private String appId;
    private long timestamp;
    private String nonceStr;
    private String signature;


}
