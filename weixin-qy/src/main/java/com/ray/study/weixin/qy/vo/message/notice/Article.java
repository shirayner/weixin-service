package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文章
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Article {
	//是	标题，不超过128个字节，超过会自动截断
	private String title;	
	//否	描述，不超过512个字节，超过会自动截断
	private String description;	
	//是	点击后跳转的链接。
	private String url;	
	//否	图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080。
	private String picurl;

}
