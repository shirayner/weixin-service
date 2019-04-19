package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文本
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Text {
	//是	消息内容，最长不超过2048个字节
	private String content;

}
