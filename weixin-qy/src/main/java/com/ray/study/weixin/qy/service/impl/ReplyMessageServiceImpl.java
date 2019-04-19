package com.ray.study.weixin.qy.service.impl;

import com.alibaba.fastjson.JSON;
import com.ray.study.weixin.common.http.HttpHelper;
import com.ray.study.weixin.qy.AgentAuthConfig;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import com.ray.study.weixin.qy.service.ReplyMessageService;
import com.ray.study.weixin.qy.util.MessageUtil;
import com.ray.study.weixin.qy.vo.message.reply.response.TextMessage;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 23:07
 * @desc :
 **/
@Service
@Slf4j
public class ReplyMessageServiceImpl implements ReplyMessageService {

	@Autowired
	HttpHelper httpHelper;

	@Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private String token;

	private String encodingAesKey;


	/**
	 * 1.校验回调url
	 * @param msgSignature 签名
	 * @param timeStamp  时间戳
	 * @param nonce 随机串
	 * @param echoStr 校验结果
	 * @return
	 */
	@Override
	public String verifyURL(String msgSignature, String timeStamp, String nonce, String echoStr) {
		WXBizMsgCrypt wxcpt = null;
		String sEchoStr="";
		try {
			//1. 进行url校验
			List<AgentAuthConfig> agentAuthConfigList = weiXinAuthConfig.getAgent();
			for(AgentAuthConfig agentAuthConfig : agentAuthConfigList){
				wxcpt = new WXBizMsgCrypt(agentAuthConfig.getToken(),
						agentAuthConfig.getEncodingAesKey(),
						weiXinAuthConfig.getCorpId());
				//不抛异常就说明校验成功
				boolean isVerifySuccess = wxcpt.verifyURL_WXQY(msgSignature, timeStamp, nonce, echoStr);
				if(isVerifySuccess){
					this.encodingAesKey = agentAuthConfig.getEncodingAesKey();
					this.token = agentAuthConfig.getToken();

					return wxcpt.decryptEchoStr(echoStr);
				}
			}

			log.error("=============回调url校验失败");

		} catch (AesException e) {
			log.error("回调url校验失败", e.getMessage());
		}
		return sEchoStr;
	}

	/**
	 * 1.回复消息
	 * 	   企业微信的消息都是加密消息，没有明文消息
	 * @param request
	 * @return
	 */
	@Override
	public String reply(HttpServletRequest request) {

		String parms=JSON.toJSONString(request.getParameterMap());
		log.info("parms:{}",parms);

		//1.解密：从request中获取消息明文
		String xmlMsg=getDecryptMsg(request);

		//2.获取回复消息(明文)
		String replyMsg = getEncryptReplyMsg( request, xmlMsg);

		return replyMsg;
	}


	/**
	 * @desc ：2.从request中获取明文消息
	 * 	  从request中获取加密消息，将其解密并返回
	 * @param request
	 * @return
	 */
	@Override
	public String getDecryptMsg(HttpServletRequest request) {
		String result="";     // 明文，解密之后的结果

		String msgSignature = request.getParameter("msg_signature"); // 微信加密签名
		String timeStamp = request.getParameter("timestamp");   // 时间戳
		String nonce = request.getParameter("nonce");          // 随机数

		//1.获取加密的请求消息：使用输入流获得加密请求消息postData
		String encryptPostData=getEncryptMsg( request);  // 密文，对应POST请求的数据
		log.debug("请求的加密消息：{}",encryptPostData);

		//2.获取消息明文：对加密的请求消息进行解密获得明文
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(getToken(), getEncodingAesKey(), weiXinAuthConfig.getCorpId());
			result=wxcpt.DecryptMsg(msgSignature, timeStamp, nonce, encryptPostData);
		} catch (AesException e) {
			log.error("请求消息解密失败", e.getMessage());
		}

		log.debug("请求的解密消息：{}",result);
		return result;
	}

	/** 获取待返回的加密消息
	 *
	 * @param xmlMsg
	 * @return
	 */
	@Override
	public String getEncryptReplyMsg(HttpServletRequest request,String xmlMsg) {

		//1.获取明文回复消息
		String plaintextReplyMsg=getPlaintextReplyMsg( xmlMsg);


		//2.将明文回复消息进行加密
		String encryptReplyMsg="";
		String timeStamp = request.getParameter("timestamp");   // 时间戳
		String nonce = request.getParameter("nonce");          // 随机数

		try{
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(getToken(), getEncodingAesKey(), weiXinAuthConfig.getCorpId());
			encryptReplyMsg=wxcpt.EncryptMsg(plaintextReplyMsg, timeStamp, nonce);
		} catch (AesException e) {
			log.error("明文回复消息加密失败{}", e.getMessage());
		}

		return encryptReplyMsg;
	}

	/** 获取明文的回复消息
	 *
	 * @param xmlMsg
	 * @return
	 */
	private String getPlaintextReplyMsg(String xmlMsg) {
		String replyMsg = null;

		try {
			//2.解析微信发来的请求,解析xml字符串
			Map<String, String> requestMap= MessageUtil.parseXml(xmlMsg);

			//3.获取请求参数
			//3.1 企业微信CorpID
			String fromUserName = requestMap.get("FromUserName");
			//3.2 成员UserID
			String toUserName = requestMap.get("ToUserName");
			//3.3 消息类型与事件
			String msgType = requestMap.get("MsgType");
			String eventType = requestMap.get("Event");
			String eventKey = requestMap.get("EventKey");
			log.debug("fromUserName:"+fromUserName+" toUserName:"+toUserName+"  msgType:"+msgType+"Event:"+eventType+"  eventKey:"+eventKey);


			//4.组装 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			//4.1.获取回复消息的内容 ：消息的分类处理
			String replyContent=getReplyContentByMsgType(msgType, eventType, eventKey);
			textMessage.setContent(replyContent);
			log.debug("replyContent：{}", replyContent);

			//5.获取xml字符串： 将（被动回复消息型的）文本消息对象 转成  xml字符串
			replyMsg = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return replyMsg;
	}

	/**
	 * @desc ：3.处理消息：根据消息类型获取回复内容
	 *
	 * @param msgType
	 * @return String
	 */
	private static  String getReplyContentByMsgType(String msgType,String eventType,String eventKey){
		String replyContent="";
		//1.文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			replyContent = "您发送的是文本消息！";

		}
		//2.图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			replyContent = "您发送的是图片消息！";
		}
		//3.地理位置消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

			replyContent = "您发送的是地理位置消息 ！";
		}
		//4.链接消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			replyContent = "您发送的是链接消息！";
		}
		//5.音频消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			replyContent = "您发送的是音频消息！";
		}
		//6.事件推送
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			replyContent=getReplyContentByEventType(eventType, eventKey);
		}
		//7.请求异常
		else {
			replyContent="请求处理异常，请稍候尝试！";
		}

		return replyContent;
	}

	private static String getReplyContentByEventType(String eventType,String eventKey){

		String respContent="";
		// 订阅
		if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
			respContent = "欢迎关注！";
		}
		// 取消订阅
		else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
			// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
		}
		//上报地理位置事件
		else if(eventType.equals("LOCATION")){

		}
		// 自定义菜单点击事件
		else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

			if (eventKey.equals("12")) {

			} else if (eventKey.equals("13")) {
				respContent = "周边搜索菜单项被点击！";
			} else if (eventKey.equals("14")) {
				respContent = "历史上的今天菜单项被点击！";
			} else if (eventKey.equals("21")) {
				respContent = "歌曲点播菜单项被点击！";
			} else if (eventKey.equals("22")) {

				respContent = "经典游戏菜单项被点击！";
			} else if (eventKey.equals("23")) {
				respContent = "美女电台菜单项被点击！";
			} else if (eventKey.equals("24")) {
				respContent = "人脸识别菜单项被点击！";
			} else if (eventKey.equals("25")) {
				respContent = "聊天唠嗑菜单项被点击！";
			} else if (eventKey.equals("31")) {
				respContent = "Q友圈菜单项被点击！";
			} else if (eventKey.equals("32")) {
				respContent = "电影排行榜菜单项被点击！";
			} else if (eventKey.equals("33")) {
				respContent = "幽默笑话菜单项被点击！";
			}
		}
		return respContent;
	}


	/**
	 * 获取请求的加密消息
	 * @param request
	 * @return   密文，对应POST请求的数据
	 */
	private String getEncryptMsg(HttpServletRequest request){
		ServletInputStream  inputStream = null;
		try {
			inputStream = request.getInputStream();
		} catch (IOException e) {
			log.error("获取加密消息失败：{}",e.getMessage());
		}

		return getStringFromInputStream( inputStream);
	}


	private String getStringFromInputStream(InputStream inputStream){
		String postData="";   // 密文，对应POST请求的数据
		try {
			BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
			String tempStr="";   //作为输出字符串的临时串，用于判断是否读取完毕
			while(null!=(tempStr=reader.readLine())){
				postData+=tempStr;
			}
		} catch (IOException e) {
			log.error("解析输入流失败：{}",e.getMessage());
		}
		return postData;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}
}
