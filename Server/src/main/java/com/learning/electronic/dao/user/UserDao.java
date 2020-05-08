package com.learning.electronic.dao.user;

import com.learning.electronic.bean.login.SignInReqBo;
import com.learning.electronic.bean.user.UserInfo;

import java.sql.SQLException;

/**
 * @author chenglonghy
 * @date 2020/4/22
 * <p>
 * 功能说明:
 */
public interface UserDao {

    int insertUserInfo(SignInReqBo signInReqBo) throws SQLException;

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     * @throws SQLException
     */
    UserInfo selectUserInfo(String name) throws SQLException;

}
