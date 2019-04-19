package com.ray.study.weixin.qy.vo.message.reply.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc  : 图片消息
 * 
 * @author: shirayner
 * @date  : 2017-8-17 下午1:53:28
 */
@Getter
@Setter
@NoArgsConstructor
public class ImageMessage extends BaseMessage {

	private Media Image;


}

