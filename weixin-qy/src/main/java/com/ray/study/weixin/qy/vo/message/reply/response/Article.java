package com.ray.study.weixin.qy.vo.message.reply.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc  : 图文
 * 
 * @author: shirayner
 * @date  : 2017-8-17 下午2:02:33
 */
@Getter
@Setter
@NoArgsConstructor
public class Article {
    // 图文消息名称
    private String Title;
    // 图文消息描述
    private String Description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
    private String PicUrl;
    // 点击图文消息跳转链接
    private String Url;


}