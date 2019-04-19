package com.ray.study.weixin.qy.vo.message.reply.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  图片消息 
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ImageMessage extends BaseMessage {  
	// 图片链接  
	private String PicUrl;  
	// 图片媒体文件id，可以调用获取媒体文件接口拉取
	private String MediaId;  


}  