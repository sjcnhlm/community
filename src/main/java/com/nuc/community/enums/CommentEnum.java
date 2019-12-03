package com.nuc.community.enums;

public enum  CommentEnum {
    QUSESTION(1),
    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentEnum(Integer type)
    {
        this.type = type;
    }
}
