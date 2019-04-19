package com.ray.study.weixin.qy.vo.message.reply.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 语音消息 
 * @author shirayner
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class VoiceMessage extends BaseMessage {  
    // 语音媒体文件id，可以调用获取媒体文件接口拉取数据 
    private String MediaId;  
    // 语音格式，如amr，speex等
    private String Format;  
  

}  