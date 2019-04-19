package com.ray.study.weixin.qy.controller;


import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.qy.service.NoticeService;
import com.ray.study.weixin.qy.vo.message.notice.MediaVO;
import com.ray.study.weixin.qy.vo.message.notice.TextCardVO;
import com.ray.study.weixin.qy.vo.message.notice.TextVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 22:29
 * @desc : 消息推送之发送通知
 *   即主动发送消息
 **/
@Api("消息推送")
@RestController
@RequestMapping("/msg/")
@Slf4j
public class NoticeController {

    @Autowired
	NoticeService noticeService;

	@ApiOperation("发送文本消息")
	@ApiImplicitParam(name = "textVO", value = "文本消息值对象", required = true, dataType = "TextVO")
    @PostMapping("text")
    public ServerResponse<String> sendTextNotice(@RequestBody TextVO textVO){
        boolean isSuccess= noticeService.sendTextNotice(textVO.getUserId(), textVO.getContent());
        if(isSuccess){
            return ServerResponse.createBySuccessMessage("消息发送成功");
        }
        return ServerResponse.createByErrorMessage("消息发送失败");
    }

	@ApiOperation("发送文本卡片消息")
	@ApiImplicitParam(name = "textCardVO", value = "文本卡片消息值对象", required = true, dataType = "TextCardVO")
	@PostMapping("textCard")
    public ServerResponse<String> sendTextCardNotice(@RequestBody TextCardVO textCardVO){
        boolean isSuccess= noticeService.sendTextCardNotice(textCardVO.getUserId(), textCardVO.getTitle(), textCardVO.getDescription() , textCardVO.getDescUrl());
        if(isSuccess){
            return ServerResponse.createBySuccessMessage("消息发送成功");
        }
        return ServerResponse.createByErrorMessage("消息发送失败");
    }

	@ApiOperation("发送图片消息")
	@ApiImplicitParam(name = "mediaVO", value = "图片消息值对象", required = true, dataType = "MediaVO")
	@PostMapping("img")
    public ServerResponse<String> sendImgNotice(@RequestBody MediaVO mediaVO){
        boolean isSuccess= noticeService.sendImgNotice(mediaVO.getUserId(), mediaVO.getMediaId());
        if(isSuccess){
            return ServerResponse.createBySuccessMessage("消息发送成功");
        }
        return ServerResponse.createByErrorMessage("消息发送失败");
    }

}
