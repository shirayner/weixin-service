package com.ray.study.weixin.qy.vo.message.notice;

import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/15
 */
@Getter
@Setter
@NoArgsConstructor
public class TextCardVO  extends BaseVO {
	private String userId;
	//是 标题，不超过128个字节，超过会自动截断
	private String title;

	//是	描述，不超过512个字节，超过会自动截断
	private String description;

	//是	点击后跳转的链接。
	private String descUrl;


}
