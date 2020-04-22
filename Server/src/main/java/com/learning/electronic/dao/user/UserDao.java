package com.learning.electronic.dao.user;

import com.learning.electronic.bean.user.UserInfoBo;

import java.sql.SQLException;

/**
 * @author chenglonghy
 * @date 2020/4/22
 * <p>
 * 功能说明:
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     * @throws SQLException
     */
    UserInfoBo selectUserInfo(String name) throws SQLException;
}
