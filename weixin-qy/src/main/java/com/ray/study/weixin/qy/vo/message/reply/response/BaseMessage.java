package com.ray.study.weixin.qy.vo.message.reply.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 消息基类（企业微信 -> 普通用户）
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseMessage {
    // 成员UserID
    private String ToUserName;
    // 企业微信CorpID
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型
    private String MsgType;


}