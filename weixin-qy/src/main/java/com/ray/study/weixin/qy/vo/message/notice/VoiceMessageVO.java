package com.ray.study.weixin.qy.vo.message.notice;

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
public class VoiceMessageVO extends BaseMessageVO {
    //语音
    private Media voice ;
    //否	 表示是否是保密消息，0表示否，1表示是，默认0
    private int safe;
    
	public Media getVoice() {
		return voice;
	}
	public void setVoice(Media voice) {
		this.voice = voice;
	}
	public int getSafe() {
		return safe;
	}
	public void setSafe(int safe) {
		this.safe = safe;
	}
   
    

   
}  