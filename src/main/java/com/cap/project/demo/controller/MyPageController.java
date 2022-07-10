package com.cap.project.demo.controller;

import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.config.auth.PrincipalDetailsForExpert;
import com.cap.project.demo.domain.Department;
import com.cap.project.demo.dto.request.ExpertUpdateDto;
import com.cap.project.demo.dto.request.PasswordCheckDto;
import com.cap.project.demo.dto.request.UserUpdateDto;
import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.DepartmentRepository;
import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyPageController {

    /**
     * 1. 자신의 정보 나오기 O
     *
     * 2. 회원 정보 수정
     *      2.1 아이디 변경 X , 비밀변경  X , 기본적인 자신의 프로필 정보 변경하기 O
     *
     * 3. 아이디 찾기, 비밀번호 찾기 X
     *
     * 4. 회원 탈퇴 O
     */

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private DepartmentRepository departmentRepository;


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

    // check password for withdrawal
    @PostMapping("/password/checkForWithdrawal")
    public String checkPasswordForWithdrawal(@ModelAttribute PasswordCheckDto passwordCheckDto,
                                             Authentication authentication, Model model) {

        // 1. 우선 html에서 js로 인증되지 않는 두개의 비밀번호가 일치하는지 확인하는 작업 후 회원탈퇴 버튼 활성화 (프론트 처리)

        // 2. 2개의 비밀번호가 일치하다는 가정하에 컨트롤러로 들오온다.

        // 3. 컨트롤러로 들어오면 principalDetail를 통해서 로그인한 사용자의 실제 비밀번호랑 일치하는지 확인 ("메시지 직접 출력")

            // 3.1 만약 일치하면 실질적인 회원탈퇴 진행 후 , 로그아웃 된 상태로 main 페이지 이동

            // 3.2 만약 일치하지 않는다면 , 일치하지 않는다는 메시지를 띄워주고 다시 비밀번호를 입력하는 페이지로 이동

        // check authentication class type
        if(authentication.getPrincipal() instanceof PrincipalDetails){
            String status = userService.checkPasswordForWithdrawal(passwordCheckDto, (PrincipalDetails) authentication.getPrincipal());

            if(status.equals("success")) {
                model.addAttribute("message", "회원탈퇴를 완료하였습니다.");
                return "redirect:/logout";
            } else {
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                return "redirect:/mypage/withdrawal";
            }

        }else{
            String status = expertService.checkPasswordForWithdrawal(passwordCheckDto, (PrincipalDetailsForExpert) authentication.getPrincipal());

            if(status.equals("success")) {
                model.addAttribute("message", "회원탈퇴를 완료하였습니다.");
                return "redirect:/logout";
            } else {
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                return "redirect:/mypage/withdrawal";
            }
        }

    }


    /**
     * 프로필의 정보를 변경하기 위한 폼을 반환한다.
     * 해당 폼에는 로그인한 사람의 기본적인 정보가 담겨있다.
     * @Return String : 폼의 주소
     */
    @GetMapping("/mypage/profile")
    public String getProfileForm(Authentication authentication, Model model) {

        //  일반 환자의 경우 , 아이디 비밀번호 변경은 따로 진행 예정
        if(authentication.getPrincipal() instanceof PrincipalDetails){

            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

            UserResponse userResponse = userService.getUserInfo(principal.getUser().getDb_id());

            model.addAttribute("user", userResponse);

            return "mypage/modifyUser";

        }else{

            PrincipalDetailsForExpert principal = (PrincipalDetailsForExpert) authentication.getPrincipal();

            ExpertResponse expertResponse = expertService.getExpertInfo(principal.getExpert().getDb_id());

            model.addAttribute("expert", expertResponse);

            List<Department> departments = departmentRepository.findAll();

            model.addAttribute("departments", departments); // 부서의 목록을 보여준다.

            return "mypage/modifyExpert";

        }
    }

    /**
     * 프론트에서 전달받은 정보를 통해서 회원 정보를 변경한다.
     *
     */
    @PutMapping("/mypage/patient/profile")
    public String updatePatientProfile(UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDetails principalDetails
                                ,Model model) {

        String status = userService.updateUserInfo(userUpdateDto, principalDetails.getUser().getDb_id());

        if(status.equals("success")) {
            model.addAttribute("message", "회원정보가 수정되었습니다.");
            return "redirect:/mypage/patient";
        } else {
            model.addAttribute("message", "회원정보 수정에 실패하였습니다.");
            return "redirect:/mypage/patient";
        }
    }

    /**
     * 프론트에서 전달받은 정보를 통해서 심라 상담가 정보를 변경한다.
     */
    @PutMapping("/mypage/expert/profile")
    public String updateExpertProfile(ExpertUpdateDto expertUpdateDto, @AuthenticationPrincipal PrincipalDetailsForExpert principalDetailsForExpert
                                , Model model) {

        String status = expertService.updateExpertInfo(expertUpdateDto, principalDetailsForExpert.getExpert().getDb_id());

        if(status.equals("success")) {
            model.addAttribute("message", "회원정보가 수정되었습니다.");
            return "redirect:/mypage/expert";
        } else {
            model.addAttribute("message", "회원정보 수정에 실패하였습니다.");
            return "redirect:/mypage/expert";
        }
    }



}
