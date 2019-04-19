package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 图片、语音、文件
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Media {
	//是	 图片/语音/文件 媒体文件id，可以调用上传临时素材接口获取
	private String media_id;

	
}
