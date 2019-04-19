package com.ray.study.weixin.qy.vo.message.reply.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 被动回复消息
 *
 * 消息基类（普通用户 -> 企业微信） 
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseMessage {  
    // 开发者微信号  
    private String ToUserName;  
    // 发送方帐号（一个OpenID）  
    private String FromUserName;  
    // 消息创建时间 （整型）  
    private long CreateTime;  
    // 消息类型（text/image/location/link）  
    private String MsgType;  
    // 消息id，64位整型  
    private long MsgId;  
    //企业应用的id，整型。可在应用的设置页面查看
    private int AgentID;
  

    
}  