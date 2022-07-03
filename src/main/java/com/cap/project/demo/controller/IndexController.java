package com.cap.project.demo.controller;

import com.cap.project.demo.dto.request.ExpertJoinRequest;
import com.cap.project.demo.dto.request.ExpertJoinRequestForm;
import com.cap.project.demo.dto.request.UserJoinRequest;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class IndexController {

    @Autowired
    public UserService userService;

    @Autowired
    public ExpertService expertService;

    @GetMapping("/")
    public String main(Model model) {
        return "MainPage/index";
    }

    /**
     * 로그인 폼을 받을 수 있는 컨트롤러
     * @param alertmsg 오류 메시지 담는 변수
     * @param model
     * @return mainPage 패키지 아래 login.html로 이동한다.
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "alertmsg" , required = false) String alertmsg, Model model) {

        model.addAttribute("alertmsg", alertmsg);

        return "MainPage/login";
    }

    /**
     * 심리 환자 회원가입 url
     * @return 회원가입 폼
     */
    @GetMapping("/join/patient")
    public  String joinFormForPatient(){
        return "MainPage/testUserForm";
    }

    /**
     * 심리 상담가 회원가입 url
     * @return 회원가입 폼
     */
    @GetMapping("/join/expert")
    public  String joinFormForExpert(){
        return "MainPage/testExpertForm";
    }





    /**
     * 회원 가입 폼에서 날라온 데이터를 통해 심리 환자 회원가입 진행
     * @param userJoinRequest
     * @return 회원 가입이 완료되면 다시 login 하도록 한다.
     */
    @PostMapping(value = "/join/patient" )
    public String joinForPatient(UserJoinRequest userJoinRequest){
        UserResponse userDto = userService.joinForPatient(userJoinRequest);

        return "redirect:/login";
    }

    /**
     * 회원 가입 폼에서 날라온 데이터를 통해 심리 상담가 회원가입 진행
     * @param expertJoinRequestForm
     * @return 회원 가입이 완료되면 다시 login 하도록 한다.
     */
    @PostMapping(value = "/join/expert" )
    public String joinForExpert(@ModelAttribute ExpertJoinRequestForm expertJoinRequestForm) throws IOException {

        ExpertJoinRequest expertJoinRequest = expertJoinRequestForm.createExpertJoinRequest();
        expertService.joinForExpert(expertJoinRequest);

        return "redirect:/login";
    }


}
