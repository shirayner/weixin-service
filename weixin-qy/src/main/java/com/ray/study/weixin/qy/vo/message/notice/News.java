package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 图文
 * @author shirayner
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class News {
	 //文章列表
	 private List<Article> articles;
	 
}
