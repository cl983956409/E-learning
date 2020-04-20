package com.learning.electronic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/17 - 17:02
 * @history 2020/4/17 - 17:02 chenglonghy  create.
 */
@Slf4j
@EnableWebSecurity
//@SpringBootConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http是根对象，其下有4个配置项：authorizeRequests、formLogin、logout、csrf;每个配置项使用and方法分隔连接;
        http
                .csrf().disable()//禁止csrf校验
                //配置权限
                .authorizeRequests()
                .antMatchers("/", "/test/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .and()//每个配置项使用and方法分隔连接
                //开启formLogin默认配置
                .formLogin()
                //.loginPage("/login.html")//用户未登录时，访问任何资源都转跳到该路径，即登录页面
                //.loginProcessingUrl("/login")//登录表单form中action的地址，也就是处理认证请求的路径
                .usernameParameter("username")///登录表单form中用户名输入框input的name名，不修改的话默认是username
                .passwordParameter("password")//form中密码输入框input的name名，不修改的话默认是password
                //.defaultSuccessUrl("/test")//登陆成功后默认跳转的路径
                .and()
                ////配置注销
                //.logout()
                //.logoutUrl("/logout")
                //.logoutSuccessUrl("/login/auth")
                //.and()
        ;
    }

    /**
     * 在内存中创建一个名为 "user" 的用户，密码为 "pwd"，拥有 "USER" 权限
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("cltest").password("cmcc123456").roles("USER").build());
        return manager;
    }

}
