package com.ray.study.weixin.common.constant;

public enum MsgtypeEnum {
    TEXT("text"),
    TEXT_CARD("textcard"),
    IMAGE("image");

    private final String desc;

    MsgtypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
