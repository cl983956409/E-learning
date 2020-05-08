package com.learning.electronic.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/30 - 11:07
 * @history 2020/4/30 - 11:07 chenglonghy  create.
 */

@Controller
public class UserController {

    // 在登陆成功后，通过 Principal 获取登录人信息，类似于session

    // @AuthenticationPrincipal 认证身份主体

    // 用户登录
    @RequestMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user/user";
    }

    // 管理员登录
    @RequestMapping("/admin")
    public String admin(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "admin/admin";
    }
}
