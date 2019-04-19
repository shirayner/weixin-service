package com.ray.study.weixin.qy.vo.message.notice;

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
public class TextMessageVO extends BaseMessageVO {
    //文本
    private Text text;
    //否	 表示是否是保密消息，0表示否，1表示是，默认0
    private int safe;

  
   
}  