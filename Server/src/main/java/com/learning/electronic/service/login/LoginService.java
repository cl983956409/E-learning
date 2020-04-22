package com.learning.electronic.service.login;

import com.learning.electronic.bean.login.LoginReqBo;

/**
 * @author chenglonghy
 * @date 2020/4/22
 * <p>
 * 功能说明:
 */
public interface LoginService {

    /**
     * 校验用户信息
     *
     * @param loginReqBo
     * @return
     */
    public boolean verifyUserPermissions(LoginReqBo loginReqBo);
}
