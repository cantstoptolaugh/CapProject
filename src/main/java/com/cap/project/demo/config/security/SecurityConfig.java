package com.cap.project.demo.config.security;

import com.cap.project.demo.config.Oauth2.PrincipalOauth2UserService;
import com.cap.project.demo.config.auth.LoginFailureHandler;
import com.cap.project.demo.config.auth.LoginSuccessHandler;
import com.cap.project.demo.config.auth.WebAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginFailureHandler loginFailureHandler; // 로그인 실패시 처리하는 클래스

    private final LoginSuccessHandler loginSuccessHandler; // 로그인 성공시 처리하는 클래스

    private final WebAccessDeniedHandler webAccessDeniedHandler; // 권한 없을때 처리하는 클래스

    private final PrincipalOauth2UserService principalOauth2UserService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(LoginFailureHandler loginFailureHandler, LoginSuccessHandler loginSuccessHandler,
                          WebAccessDeniedHandler webAccessDeniedHandler , PrincipalOauth2UserService principalOauth2UserService
                          , BCryptPasswordEncoder passwordEncoder) {
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
        this.principalOauth2UserService = principalOauth2UserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/boardForm/** , /myPage/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login?alertmsg=" + URLEncoder.encode("로그인 필요합니다.", StandardCharsets.UTF_8))
                .loginProcessingUrl("/loginProcess")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                .and()
                .oauth2Login()
                .loginPage("/login?alertmsg=" + URLEncoder.encode("로그인 필요합니다.", StandardCharsets.UTF_8))
                .userInfoEndpoint()
                .userService(principalOauth2UserService);




    }

}
