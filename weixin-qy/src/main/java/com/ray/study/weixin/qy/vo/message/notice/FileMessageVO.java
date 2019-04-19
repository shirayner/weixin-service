package com.ray.study.weixin.qy.vo.message.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文件消息
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class FileMessageVO extends BaseMessageVO {
    //文件
    private Media file ;
    //否	 表示是否是保密消息，0表示否，1表示是，默认0
    private int safe;

}  