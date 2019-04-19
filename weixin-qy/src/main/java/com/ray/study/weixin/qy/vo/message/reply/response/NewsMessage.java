package com.ray.study.weixin.qy.vo.message.reply.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @desc  : 图文消息
 * 
 * @author: shirayner
 * @date  : 2017-8-17 下午2:03:31
 */
@Getter
@Setter
@NoArgsConstructor
public class NewsMessage extends BaseMessage {
    // 图文消息个数，限制为10条以内
    private int ArticleCount;

    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;


}