package com.ray.study.weixin.qy.vo.message.reply.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 链接消息 
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class LinkMessage extends BaseMessage {  
	// 消息标题  
	private String Title;  
	// 消息描述  
	private String Description;  
	// 封面缩略图的url
	private String PicUrl;  



}  