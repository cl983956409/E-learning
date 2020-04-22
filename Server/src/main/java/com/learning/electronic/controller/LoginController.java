package com.learning.electronic.controller;

import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:52
 * @history 2020/4/17 - 17:52 chenglonghy  create.
 */
@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping(value = "/auth")
    public Boolean login(@RequestBody LoginReqBo loginReqBo) {
        //log.info("用户名：【" + loginReqBo.getUsername() + "】、密  码：【"+loginReqBo.getPassword()+"】");
        log.debug("登录校验");
        return loginService.verifyUserPermissions(loginReqBo);
    }

    @PostMapping(value = "/getMenus")
    public List<String> getMenus() {
        return null;
    }
}
