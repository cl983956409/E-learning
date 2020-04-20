package com.learning.electronic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/20 - 10:21
 * @history 2020/4/20 - 10:21 chenglonghy  create.
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public void test() {
        log.info("调用成功");
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "user/user";
    }
}
