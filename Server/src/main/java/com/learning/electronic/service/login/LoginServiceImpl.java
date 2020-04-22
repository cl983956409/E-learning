package com.learning.electronic.service.login;

import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.bean.user.UserInfoBo;
import com.learning.electronic.dao.user.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/22 - 14:40
 * @history 2020/4/22 - 14:40 chenglonghy  create.
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    @Override
    public boolean verifyUserPermissions(LoginReqBo loginReqBo) {
        UserInfoBo userInfo = null;
        try {
            userInfo = userDao.selectUserInfo(loginReqBo.getUsername());
        } catch (SQLException e) {
            log.error("查询用户信息异常");
        }

        if (userInfo == null) {
            log.debug("用户信息查询为空，用户尚未注册");
            return false;
        } else {
            log.debug("用户信息：", userInfo.toString());
            return true;
        }
    }
}
