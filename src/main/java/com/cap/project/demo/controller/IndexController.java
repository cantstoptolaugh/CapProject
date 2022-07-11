package com.cap.project.demo.controller;

import com.cap.project.demo.domain.Department;
import com.cap.project.demo.domain.FileStore;
import com.cap.project.demo.domain.enums.AttachmentType;
import com.cap.project.demo.dto.request.ExpertJoinRequest;
import com.cap.project.demo.dto.request.ExpertJoinRequestForm;
import com.cap.project.demo.dto.request.UserJoinRequest;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.DepartmentRepository;
import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/")
    public String main(Model model , @RequestParam(value = "alertmsg" , required = false) String alertmsg) {

        model.addAttribute("alertmsg", alertmsg);
        return "mainpage/index";
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

        return "mainpage/login";
    }

    /**
     * 심리 환자 회원가입 url
     * @return 회원가입 폼
     */
    @GetMapping("/join/patient")
    public  String joinFormForPatient(){
        return "mainpage/userSignUp";
    }

    /**
     * 심리 상담가 회원가입 url
     * @return 회원가입 폼
     */
    @GetMapping("/join/expert")
    public  String joinFormForExpert(Model model){

        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments );

        return "mainPage/expertSignUp";
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


    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource processImg(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileStore.createPath(filename, AttachmentType.IMAGE));
    }


}
