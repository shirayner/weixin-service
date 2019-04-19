package com.ray.study.weixin.qy.service;

public interface NoticeService {
    Boolean sendTextNotice( String userId, String content);
    Boolean sendTextCardNotice(String userId, String title, String description, String descUrl);
    Boolean sendImgNotice(String userId, String mediaId);
}
