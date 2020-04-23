package com.learning.electronic.controller;

import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.service.login.LoginService;
import com.learning.electronic.util.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:52
 * @history 2020/4/17 - 17:52 chenglonghy  create.
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping(value = "/login/test")
    public Boolean login(@RequestBody LoginReqBo loginReqBo) {
        //log.info("用户名：【" + loginReqBo.getUsername() + "】、密  码：【"+loginReqBo.getPassword()+"】");
        log.debug("登录校验");
        return loginService.verifyUserPermissions(loginReqBo);
    }

    @GetMapping(value = "/getVercode")
    public void getVercode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("image/jpeg");

        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("index_code", text);
        VerifyCode.output(image, resp.getOutputStream());
    }
}
