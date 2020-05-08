package com.learning.electronic.enums;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/24 - 9:39
 * @history 2020/4/24 - 9:39 chenglonghy  create.
 */
public enum RespStatus {
    SUCCESS("0000", "成功"),

    ERROR("2000", "系统错误"),
    LOGIN_IN_ERROR("2001","登录失败"),
    SIGN_IN_ERROR("2002","注册失败");
    private final String code;
    private final String message;

    private RespStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
