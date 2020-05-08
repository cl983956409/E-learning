package com.learning.electronic.controller;

import com.learning.electronic.bean.component.RespObject;
import com.learning.electronic.bean.login.LoginReqBo;
import com.learning.electronic.bean.login.SignInReqBo;
import com.learning.electronic.service.login.LoginService;
import com.learning.electronic.util.RespUtils;
import com.learning.electronic.util.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 用户注册接口
     */
    @PostMapping(value = "/signIn")
    public RespObject signIn(@RequestBody SignInReqBo signInReqBo) {
        log.debug("用户注册");
        return loginService.signIn(signInReqBo);
    }

    ///**
    // * post登录接口，登录验证由系统实现
    // */
    //@PostMapping(value = "/loginProcessing")
    //public RespObject login(@RequestBody LoginReqBo loginReqBo) {
    //    log.debug("post登录接口，登录验证由系统实现");
    //    //return loginService.verifyUserPermissions(loginReqBo);
    //    return loginService.verifyUserPermissions(loginReqBo);
    //}

    /**
     * get登录接口
     */
    @GetMapping(value = "/loginPage")
    public RespObject loginGet(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.sendRedirect(request.getContextPath() + "?Redirectlogin=1");
        } catch (IOException e) {
            log.error("跳转失败");
        }
        return RespUtils.error();
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
        //todo 需要匹配一个id，将id+vercode一起存储，登录时用作校验
        //redisUtils.set(id,text,30);
    }
}
