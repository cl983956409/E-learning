package com.learning.electronic.service.login;

import com.learning.electronic.bean.component.RespObject;
import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.bean.login.SignInReqBo;

/**
 * @author chenglonghy
 * @date 2020/4/22
 * <p>
 * 功能说明:
 */
public interface LoginService {

    RespObject signIn(SignInReqBo signInReqBo);
}
