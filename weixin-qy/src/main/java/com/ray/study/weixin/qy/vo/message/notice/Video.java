package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 视频
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Video {
	//是	 视频媒体文件id，可以调用上传临时素材接口获取
	private String media_id;	
	//否	 视频消息的标题，不超过128个字节，超过会自动截断
	private String title;
	//否	 视频消息的描述，不超过512个字节，超过会自动截断
	private String description;

	
}
