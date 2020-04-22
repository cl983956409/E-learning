package com.learning.electronic.controller;

import com.learning.electronic.bean.login.LoginReqBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/20 - 10:21
 * @history 2020/4/20 - 10:21 chenglonghy  create.
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "/testUser")
    public void testUser() {
        log.info("testUser调用成功");
    }

    @GetMapping(value = "/testAdmin")
    public void testAdmin() {
        log.info("testAdmin调用成功");
    }

    @GetMapping(value = "/testIgnore")
    public void testIgnore() {
        log.info("testIgnore调用成功");
    }

    @PostMapping("/user")
    public String user(LoginReqBo test) {

        return "user/user";
    }
}
