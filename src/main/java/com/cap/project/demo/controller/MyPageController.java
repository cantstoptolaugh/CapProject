package com.cap.project.demo.controller;

import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    /**
     * 1. 자신의 정보 나오기
     *
     * 2. 회원 정보 수정
     *      2.1 아이디 변경, 비밀변경 , 기본적인 자신의 프로필 정보 변경하기
     *
     * 3. 아이디 찾기, 비밀번호 찾기
     *
     * 4. 회원 탈퇴
     */

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;


    @Secured("ROLE_USER")
    @GetMapping("/mypage/patient")
    public String myPageForPatient(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        UserResponse userResponse = userService.getUserInfo(principalDetails.getUser().getDb_id());

        model.addAttribute("user", userResponse);

        return "mypage/patient";
    }





}
