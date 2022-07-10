package com.cap.project.demo.controller;

import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.config.auth.PrincipalDetailsForExpert;
import com.cap.project.demo.dto.request.PasswordCheckDto;
import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @Secured("ROLE_EXPERT")
    @GetMapping("/mypage/expert")
    public String myPageForExpert(@AuthenticationPrincipal PrincipalDetailsForExpert principalDetails, Model model) {

        ExpertResponse expertResponse = expertService.getExpertInfo(principalDetails.getExpert().getDb_id());
        model.addAttribute("expert", expertResponse);

        return "mypage/expert";
    }

    // get password for withdrawal
    @GetMapping("/mypage/withdrawal")
    public String getPasswordForWithdrawal() {
        return "mypage/withdrawal";
    }

//    // check password for withdrawal
//    @PostMapping("/paassword/checkForWithdrawal")
//    public String checkPasswordForWithdrawal(@ModelAttribute PasswordCheckDto passwordCheckDto,
//                                             Authentication authentication) {
//
//        // 1. 우선 html에서 js로 인증되지 않는 두개의 비밀번호가 일치하는지 확인하는 작업 후 회원탈퇴 버튼 활성화 (프론트 처리)
//
//        // 2. 2개의 비밀번호가 일치하다는 가정하에 컨트롤러로 들오온다.
//
//        // 3. 컨트롤러로 들어오면 principalDetail를 통해서 로그인한 사용자의 실제 비밀번호랑 일치하는지 확인 ("메시지 직접 출력")
//
//            // 3.1 만약 일치하면 실질적인 회원탈퇴 진행 후 , 로그아웃 된 상태로 main 페이지 이동
//
//            // 3.2 만약 일치하지 않는다면 , 일치하지 않는다는 메시지를 띄워주고 다시 비밀번호를 입력하는 페이지로 이동
//
//        // check authentication class type
//        if(authentication.getPrincipal() instanceof PrincipalDetails){
//            userService.checkPasswordForWithdrawal(passwordCheckDto, (PrincipalDetails) authentication.getPrincipal());
//        }else{
//            expertService.checkPasswordForWithdrawal(passwordCheckDto, (PrincipalDetailsForExpert) authentication.getPrincipal());
//        }
//
//    }
//
//
//
//    // patient Withdrawal , before withdrawal need to check the password
//    @PostMapping("/withdrawal")
//    public
//
//
//
//




}
