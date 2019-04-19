package com.ray.study.weixin.qy.vo.message.notice;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 图文消息
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NewsMessageVO extends BaseMessageVO {
    //图文
	private News news;

}  