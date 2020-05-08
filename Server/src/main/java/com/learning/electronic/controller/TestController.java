package com.learning.electronic.controller;

import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.bean.user.UserInfo;
import com.learning.electronic.dao.user.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/20 - 10:21
 * @history 2020/4/20 - 10:21 chenglonghy  create.
 */
@Slf4j
@RestController
public class TestController {
    @Resource
    UserDao userDao;

    @GetMapping(value = "/testUser")
    @PreAuthorize("hasAnyRole('admin','user')")
    public String testUser() {
        return "testUser调用成功";
    }

    @GetMapping(value = "/testAdmin")
    @PreAuthorize("hasAnyRole('admin')")
    public String testAdmin() {
        return "testAdmin调用成功";
    }

    @GetMapping(value = "/testIgnore")
    public void testIgnore() {
        log.info("testIgnore调用成功");
        try {
            UserInfo user = userDao.selectUserInfo("test");
            log.info(user.toString());
        } catch (SQLException e) {

        }
    }

    @PostMapping("/user")
    public String user(LoginReqBo test) {

        return "user/user";
    }
}
