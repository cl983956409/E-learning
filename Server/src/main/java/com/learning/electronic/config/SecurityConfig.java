package com.learning.electronic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.electronic.bean.component.RespObject;
import com.learning.electronic.enums.RespStatus;
import com.learning.electronic.service.user.UserServiceImpl;
import com.learning.electronic.util.RespUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:02
 * @history 2020/4/17 - 17:02 chenglonghy  create.
 */
@Slf4j
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    /*
     * 权限继承
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }

    @Resource
    VerifyCodeFilter verifyCodeFilter;

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //添加自定义过滤器
        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);

        //http是根对象，其下有4个配置项：authorizeRequests、formLogin、logout、csrf;每个配置项使用and方法分隔连接;
        http
                .authorizeRequests()//开启登录配置
                .antMatchers("/swagger**/**", "/webjars/**", "/v2/**").permitAll()//swagger 相关接口
                .antMatchers("/getVercode", "/signIn", "/loginProcessing").permitAll()// 注册、获取验证码、登录
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()// 跨域预检请求
                .antMatchers("/testAdmin").hasRole("admin")//这一类接口，需要具备 admin 权限
                .antMatchers("/testUser").hasRole("user")//这一类接口，需要具备 user 权限
                .anyRequest().authenticated()//其他所有请求需要身份认证
                .and()
                .formLogin()
                //.loginPage("/loginPage")//登陆页面,get接口
                //.loginProcessingUrl("/loginProcessing")//登录处理接口，处理登录请求的URL,post接口
                .permitAll()//和表单登录相关的接口统统都直接通过
                //.failureUrl("/login/fail")//用户密码错误跳转接口，重定向
                //.failureForwardUrl()//登陆失败跳转接口，服务端跳转
                //.defaultSuccessUrl("/login/success", true)//登录成功跳转接口，重定向
                //.successForwardUrl()//登陆成功跳转接口，服务端跳转
                //.loginProcessingUrl("/login")//post登录接口，登录验证由系统实现
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();//用户认证信息
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespObject respObject = RespUtils.success();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    RespObject respBean = new RespObject(RespStatus.LOGIN_IN_ERROR, e.getMessage());
                    if (e instanceof LockedException) {
                        respBean.setMessage("账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        respBean.setMessage("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        respBean.setMessage("账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        respBean.setMessage("账户被禁用，请联系管理员!");
                    } else if (e instanceof BadCredentialsException) {
                        respBean.setMessage("用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .and()
                .rememberMe().key("chenglonghy")
                //.tokenRepository(jdbcTokenRepository())
                .and()
                .logout()
                .logoutUrl("/logout")//注销接口
                //.logoutSuccessUrl("/login/logout")//注销成功跳转接口
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                //.deleteCookies("myCookie") //删除自定义的cookie
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()//禁用 csrf
                //.rememberMe().rememberMeCookieName("remember")//开启记住我功能，相当于 cookie，默认保存两周
                /**
                 * 未获认证的请求默认会重定向到登录页，但是在前后端分离的登录中，这个默认行为则显得非常不合适
                 * 本方法实现未获认证的请求直接返回 JSON ，而不是重定向到登录页面
                 */
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                                                                  @Override
                                                                  public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException {
                                                                      resp.setContentType("application/json;charset=utf-8");
                                                                      PrintWriter out = resp.getWriter();

                                                                      RespObject respObject = RespUtils.resp(RespStatus.LOGIN_IN_ERROR);
                                                                      if (authException instanceof InsufficientAuthenticationException) {
                                                                          respObject.setData("未获认证的请求");
                                                                      }
                                                                      out.write(new ObjectMapper().writeValueAsString(respObject));
                                                                      out.flush();
                                                                      out.close();
                                                                  }
                                                              }
        )
        ;
    }

    /*
     * 【过滤拦截】
     * 过滤该地址，即该地址不走 Spring Security 过滤器链
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/testIgnore");
    }

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication()
    //            .withUser("chenglong").password("123456").roles("user")
    //            .and()
    //            .withUser("admin").password("123").roles("admin");
    //}

    //@Resource
    //private DataSource dataSource;
    //
    //@Override
    //@Bean
    //protected UserDetailsService userDetailsService() {
    //    JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
    //    //InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //    if (!manager.userExists("chenglong")) {
    //        manager.createUser(User.withUsername("chenglong").password("123456").roles("user").build());
    //    }
    //    if (!manager.userExists("admin")) {
    //        manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
    //    }
    //    return manager;
    //}

    @Resource
    UserServiceImpl userService;

    /*
    登录认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

}
