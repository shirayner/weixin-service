package com.ray.study.weixin.qy.vo.message.notice;

import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/10/16
 */
@Getter
@Setter
@NoArgsConstructor
public class TextVO  extends BaseVO {
	private String userId;
	private String content;

}
