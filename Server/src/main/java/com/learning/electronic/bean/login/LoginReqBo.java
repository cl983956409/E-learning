package com.learning.electronic.bean.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:55
 * @history 2020/4/17 - 17:55 chenglonghy  create.
 */
@Data
public class LoginReqBo {
    @NotNull(message = "username 不能为null")
    private String username;
    @NotBlank(message = "password 不能为空")
    private String password;
    private String captcha;
}
