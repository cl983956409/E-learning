package com.learning.electronic.bean.login;

import lombok.Data;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:55
 * @history 2020/4/17 - 17:55 chenglonghy  create.
 */
@Data
public class LoginReqBo {
    private String username;
    private String password;
    private String captcha;
}
