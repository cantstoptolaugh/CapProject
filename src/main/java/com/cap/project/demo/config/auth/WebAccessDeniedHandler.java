package com.cap.project.demo.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res,
                       AccessDeniedException ade) throws IOException, ServletException {

        res.setStatus(HttpStatus.FORBIDDEN.value());

        if(ade instanceof AccessDeniedException){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null && ((PrincipalDetails)authentication.getPrincipal()).getUser().getRole().getAuthority().equals("ROLE_USER")){
                req.setAttribute("msg","접근 권한 없는 사용자입니다.");
                req.setAttribute("nextPage","/");
            }else{
                req.setAttribute("msg","접근 권한 없는 사용자입니다.");
                req.setAttribute("nextPage","/");
            }

        }
        req.getRequestDispatcher("/login").forward(req,res);
    }
}
