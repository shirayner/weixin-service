package com.ray.study.weixin.qy.vo.message.reply.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 视频消息
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class VideoMessage {
	//视频媒体文件id，可以调用获取媒体文件接口拉取数据
	private String MediaId;	
	//视频消息缩略图的媒体id，可以调用获取媒体文件接口拉取数据
	private String ThumbMediaId;
	

}
