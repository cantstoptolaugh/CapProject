package com.cap.project.demo.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Configuration
public class LoginSuccessHandler  implements AuthenticationSuccessHandler {


    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    private String defaultUrl = "/";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // check authentication principal class type
        if(authentication.getPrincipal() instanceof com.cap.project.demo.config.auth.PrincipalDetails) {
            PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal(); // 이렇게 생각한 이유 loadbyusername에서 PrincipalDetails 반환하고 있어서...
            HttpSession session = request.getSession();

            session.setAttribute("user", user.getUser()); // user entity의 모든 정보를 session에 저장하는것이 아닌, 특정 정보만 담는다.


            // spring security에서 제공했던 기능을 직접 코드로 구현
            resultRedirectStrategy(request, response, authentication);

            // 로그인 실패하더라도 다시 로그인 성공하면 해당 실패 session 삭제하는 method
            clearAuthenticationAttributes(request);
        }else{
            PrincipalDetailsForExpert expert = (PrincipalDetailsForExpert) authentication.getPrincipal(); // 이렇게 생각한 이유 loadbyusername에서 PrincipalDetails 반환하고 있어서...
            HttpSession session = request.getSession();

            session.setAttribute("expert", expert.getExpert()); // user entity의 모든 정보를 session에 저장하는것이 아닌, 특정 정보만 담는다.


            // spring security에서 제공했던 기능을 직접 코드로 구현
            resultRedirectStrategy(request, response, authentication);

            // 로그인 실패하더라도 다시 로그인 성공하면 해당 실패 session 삭제하는 method
            clearAuthenticationAttributes(request);
        }

    }


    protected void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false); //  세션이 있다면 그 세션을 리턴하지만, 세션이 존재하지 않는다면 null을 리턴한다 .
        if(session==null) return; //세션이 null 즉, 세션에 에러가 없다면 그냥 return 된다.

        //WebAttributes.AUTHENTICATION_EXCEPTION 이름 값으로 정의된 세션을 지운다.
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);


    }

    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 로그인 권한 문제로 /login으로 들어왔을 경우, 해당 주소를 기억하고 , 성공하면 해당 주소로 다시 Redirect
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }

    }

}
