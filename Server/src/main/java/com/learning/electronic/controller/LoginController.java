package com.learning.electronic.controller;

import com.learning.electronic.bean.login.LoginReqBo;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:52
 * @history 2020/4/17 - 17:52 chenglonghy  create.
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @PostMapping(value = "/auth")
    public Boolean login(@RequestBody LoginReqBo loginReqBo) {
        if (StringUtils.isEmpty(loginReqBo.getUsername())){
            return false;
        }
        return true;
    }

    @PostMapping(value = "/getMenus")
    public List<String> getMenus() {
        return null;
    }
}
