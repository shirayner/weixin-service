package com.ray.study.weixin.qy.vo.message.reply.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文本消息
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

}