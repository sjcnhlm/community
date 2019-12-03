package com.nuc.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不在了，请重试~"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或回复"),
    NOT_LOG_IN(2003,"用户未登录，请先登录");
    ;

    private Integer code;
    private String message;


    @Override
    public String getMessage()
    {
        return  message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
