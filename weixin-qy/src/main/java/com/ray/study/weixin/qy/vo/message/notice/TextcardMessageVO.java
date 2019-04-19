package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文本卡片消息
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TextcardMessageVO extends BaseMessageVO {
    //文本
    private Textcard textcard;
    
    //btntxt	否	按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。

   

   
}  