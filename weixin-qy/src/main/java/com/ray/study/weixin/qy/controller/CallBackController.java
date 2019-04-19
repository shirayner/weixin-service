package com.ray.study.weixin.qy.controller;


import com.ray.study.weixin.qy.annotation.IgnoreToken;
import com.ray.study.weixin.qy.service.ReplyMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 22:29
 * @desc : 消息推送之发送通知
 *   即主动发送消息
 **/
@Api("接收消息服务器设置")
@RestController
@RequestMapping("/msg/")
@Slf4j
@IgnoreToken
public class CallBackController {

    @Autowired
	ReplyMessageService replyMessageService;

    /**
     * 1.接收  微信消息和事件  的请求
     * @param request
     * @return
     */
	@ApiOperation(value="回调地址", notes="接收微信消息和事件")
    @PostMapping("callBack")
    public String callBackPost(HttpServletRequest request){
        //1.调用消息业务类接收消息、处理消息
        log.debug("callBack-post====================");
        return replyMessageService.reply(request);
    }


    /**
     *  2.接收回调模式请求，验证回调url
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param echoStr
     * @return
     */
	@ApiOperation(value="回调地址", notes="接收回调模式请求，验证回调url")
    @GetMapping("callBack")
    public String callBackGet(@RequestParam("msg_signature") String msgSignature, @RequestParam("timestamp") String timeStamp
            , @RequestParam("nonce") String nonce, @RequestParam("echostr") String echoStr){
        log.debug("callBack-get====================");
        String sEchoStr=replyMessageService.verifyURL(msgSignature, timeStamp, nonce, echoStr);
        return sEchoStr;
    }

}
