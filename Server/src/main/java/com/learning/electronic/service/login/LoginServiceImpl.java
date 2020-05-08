package com.learning.electronic.service.login;

import com.learning.electronic.bean.component.RespObject;
import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.bean.login.SignInReqBo;
import com.learning.electronic.bean.user.UserInfo;
import com.learning.electronic.dao.user.UserDao;
import com.learning.electronic.dao.user.UserRoleDao;
import com.learning.electronic.enums.RespStatus;
import com.learning.electronic.util.RespUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public RespObject signIn(SignInReqBo signInReqBo) {
        try {
            //判断用户是否已存在
            UserInfo userInfo = userDao.selectUserInfo(signInReqBo.getUsername());
            if (userInfo != null) {
                return RespUtils.resp(RespStatus.SIGN_IN_ERROR,"用户已存在，请登录");
            }
            //不存在则插入新用户信息
            signInReqBo.setPass(passwordEncoder.encode(signInReqBo.getPass()));
            userDao.insertUserInfo(signInReqBo);
            if (signInReqBo.getId() > 0) {
                int res = userRoleDao.insertUserRole(signInReqBo.getId(), signInReqBo.getRoles());
            }
        } catch (SQLException e) {
            log.error("用户信息插入数据库异常");
            return RespUtils.error("数据库异常");
        }
        return RespUtils.success();
    }
}
