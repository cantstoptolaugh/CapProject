package com.cap.project.demo.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 회원탈퇴시 /logout?withdrawal=success 로 request가 들어온다.
        String withdrawal = request.getParameter("withdrawal");

        // withdrawal에 값이 있으면 회원 탈퇴 메세지 , 그렇지 않으면 일반적인 로그아웃 메시지
        if(withdrawal != null) {
            response.sendRedirect("/?alertmsg=" + URLEncoder.encode("회원탈퇴 성공하였습니다.", StandardCharsets.UTF_8));
        }else {
            response.sendRedirect("/?alertmsg=" + URLEncoder.encode("로그아웃 되었습니다.", StandardCharsets.UTF_8));
        }
    }
}
