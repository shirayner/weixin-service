package com.ray.study.weixin.qy.vo.message.notice;

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
public class VideoMessageVO extends BaseMessageVO {
    //视频
    private Video video ;
    //否	 表示是否是保密消息，0表示否，1表示是，默认0
    private int safe;

}  