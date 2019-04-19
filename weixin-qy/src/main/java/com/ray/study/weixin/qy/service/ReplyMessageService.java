package com.ray.study.weixin.qy.service;

import javax.servlet.http.HttpServletRequest;

public interface ReplyMessageService {

    /** 1.回调模式   校验url **/
    String verifyURL(String msgSignature, String timeStamp, String nonce, String echoStr);

    /** 1.回复消息 **/
    String reply(HttpServletRequest request);

    /** 2.从request中获取消息明文 **/
    String getDecryptMsg(HttpServletRequest request);

    /** 3.加密后的回复消息**/
    String getEncryptReplyMsg(HttpServletRequest request, String xmlMsg);


}
