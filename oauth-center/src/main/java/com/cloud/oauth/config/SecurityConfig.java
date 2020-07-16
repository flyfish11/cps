package com.cloud.oauth.config;

import com.cloud.oauth.adauthenication.AdAuthenticationSecurityConfig;
import com.cloud.oauth.adauthenication.AdCheckFilter;
import com.cloud.oauth.handler.OauthLogoutHandler;
import com.cloud.oauth.handler.OauthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * spring security配置
 *
 * @author com.hlxd
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    public UserDetailsService userDetailsService;

    @Autowired
    private OauthLogoutHandler oauthLogoutHandler;

    @Autowired
    private AdAuthenticationSecurityConfig adAuthenticationSecurityConfig;

    @Autowired
    private AdCheckFilter adCheckFilter;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 全局用户信息
     *
     * @param auth 认证管理
     * @throws Exception 用户认证异常信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 认证管理
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * http安全配置
     *
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //HttpSecurity.antMatcher(...)表示该安全规则只针对参数指定的路径进行过滤
        //HttpSecurity.requestMatchers同上，唯一区别是可以接受多个参数【两者不能同时使用】
        //HttpSecurity.authorizeRequests()该方法用于配置权限，如hasAnyRole、access(...)
        //.authorizeRequests().anyRequest()除去已经配置了的路径之外的其他路径，即包含在(1)/(2)中不包含在
        // HttpSecurity.authorizeRequests().antMatchers(…)中的其他路径

        http.apply(adAuthenticationSecurityConfig)
                .and()
                .addFilterAfter(this.adCheckFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable()
                .anonymous()
                .disable()
                //security 请求匹配
                .requestMatchers()
                .antMatchers("/login","/form/login","/oauth/**","/logout","/adLogin")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/form/login")
                .successHandler(myAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutSuccessHandler(new OauthLogoutSuccessHandler())
                /*   .addLogoutHandler(oauthLogoutHandler)*/
                .clearAuthentication(true)
                .and()
               /* .sessionManagement()
                .maximumSessions(1)*/;

    }

}