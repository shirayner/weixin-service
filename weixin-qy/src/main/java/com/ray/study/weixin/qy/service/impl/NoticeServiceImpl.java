package com.ray.study.weixin.qy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ray.study.weixin.common.constant.MsgtypeEnum;
import com.ray.study.weixin.common.http.HttpHelper;
import com.ray.study.weixin.common.service.AuthService;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import com.ray.study.weixin.qy.service.NoticeService;
import com.ray.study.weixin.qy.util.AgentAuthConfigLocalHelper;
import com.ray.study.weixin.qy.vo.message.notice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : shira
 * @date : 2018/4/26
 * @time : 12:28
 * @desc :
 **/
@Service
public class NoticeServiceImpl  implements NoticeService {

    /** 主动发送消息接口 **/
    private static final String SEND_NOTICE_URL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    @Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

	@Autowired
	HttpHelper httpHelper;

    @Autowired
	AuthService authService;

    private boolean doSendNotice( BaseMessageVO messageVO){
        String url=SEND_NOTICE_URL+authService.getAccessToken();
        JSONObject jsonObject = httpHelper.post(url, messageVO);
        return  httpHelper.isWXSuccess(jsonObject);
    }

    @Override
    public Boolean sendTextNotice(String userId,String content) {
        //1.创建消息
        TextMessageVO message=new TextMessageVO();
        //1.1必需
        message.setMsgtype(MsgtypeEnum.TEXT.getDesc());
        message.setAgentid(AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig().getAgentId());
        //1.2非必需
        message.setTouser(userId);

        //1.3消息内容
        Text text=new Text();
        text.setContent(content);
        message.setText(text);

        //2.发送消息
        return  doSendNotice( message);
    }


    @Override
    public Boolean sendTextCardNotice(String userId, String title, String description, String descUrl) {
        //1.创建消息
        TextcardMessageVO message=new TextcardMessageVO();
        //1.1必需
        message.setMsgtype(MsgtypeEnum.TEXT_CARD.getDesc());
        message.setAgentid(AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig().getAgentId());
        //1.2非必需
        message.setTouser(userId);

        //1.3消息内容
        Textcard textcard=new Textcard();
        textcard.setTitle(title);
        textcard.setDescription(description);
        textcard.setUrl(descUrl);

        message.setTextcard(textcard);

        //2.发送消息
        return  doSendNotice( message);
    }

    @Override
    public Boolean sendImgNotice( String userId, String mediaId) {
        //1.创建消息
        ImgMessageVO message=new ImgMessageVO();
        //1.1必需
        message.setMsgtype(MsgtypeEnum.IMAGE.getDesc());
        message.setAgentid(AgentAuthConfigLocalHelper.getInstance().getAgentAuthConfig().getAgentId());
        //1.2非必需
        //不区分大小写
        message.setTouser(userId);
        //message.setToparty("1");
        //message.setTotag(totag);
        //message.setSafe(0);

        //1.3消息内容
        Media image=new Media();
        image.setMedia_id(mediaId);
        message.setImage(image);

        //2.发送消息
        return  doSendNotice( message);
    }
}
