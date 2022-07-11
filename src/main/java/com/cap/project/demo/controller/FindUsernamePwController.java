package com.cap.project.demo.controller;

import com.cap.project.demo.service.ExpertService;
import com.cap.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class FindUsernamePwController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;


    /**
     * 사용자의 아이디를 찾는 폼을 반환한다. ( 이름, 이메일 )
     */
    @GetMapping("/user/help/username")
    public String findUsername() {
        return "help/username";
    }

    /**
     * 상담가의 아이디를 찾는 폼을 반환한다. ( 이름 , 자격증명 번호 )
     */
    @GetMapping("/expert/help/username")
    public String findExpertUsername() {
        return "help/expertUsername";
    }

    /**
     * 사용자의 비밀번호를 찾는 폼을 반환한다. ( 이름 , 아이디 , 이메일 )
     */
    @GetMapping("/user/help/password")
    public String findPassword() {
        return "help/password";
    }

    /**
     * 상담가의 비밀번호를 찾는 폼을 반환한다. ( 이름 , 아이디 , 자격증명 번호 )
     */
    @GetMapping("/expert/help/password")
    public String findExpertPassword() {
        return "help/expertPassword";
    }

    /**
     * 사용자 혹은 전문가의 기본적인 데이터를 받아서 아이디 정보를 반환한다.
     */
    @PostMapping("/help/username")
    public String findUsername(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String email ,
                               @RequestParam(required = false) String certificationNumber ,
                               Model model) {

        // 일반 사용자가 아이디를 찾을려는 경우우
       if(certificationNumber == null){
           String username = userService.findUsername(name, email);

           // replace username with * to hide username
           // username에서 중간부분은 *로 바꾼다.
           StringBuilder sb = new StringBuilder(username);
           for(int i = 3; i < username.length() - 3; i++){
               sb.setCharAt(i, '*');
           }
           username = sb.toString();

           model.addAttribute("username", username);

           return "help/username";

       }else{
            // 전문가가 아이디를 찾을려는 경우
           String expertUsername = expertService.findUsername(name, certificationNumber);

           StringBuilder sb = new StringBuilder(expertUsername);
           for(int i = 3; i < expertUsername.length() - 3; i++){
               sb.setCharAt(i, '*');
           }
           expertUsername = sb.toString();

           model.addAttribute("expertUsername", expertUsername);

           return "help/expertUsername";
       }
    }

    /**
     * 사용자, 심리 상담가의 비밀번호 정보를 반환한다.
     */
    @PostMapping("/help/password")
    @ResponseBody
    public String findPassword(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String loginId,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String certificationNumber,
                               Model model) {

        // 일반 사용자가 비밀번호를 찾을려는 경우
        if(certificationNumber == null){
            String password = userService.findPassword(name, loginId , email);

            // 아이디 혹은 회원이 존재하는 경우
            if(password != null){
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                uuidString = uuidString.replace("-", "");
                password = uuidString.substring(0, 8);

                userService.updatePassword(loginId, password);

                model.addAttribute("temporary password", password);
                return password;
            }

            // 해당 아이디 혹은 회원이 없는 경우
            return "fail_find_password";
            //model.addAttribute("password", password);
            //return "help/password";
        }else{
            // 심리 상담가의 비밀번호를 찾을려는 경우
            String password = expertService.findPassword(name, loginId , certificationNumber);

            return password;

            //model.addAttribute("password", password);
            //return "help/expertPassword";
        }
    }



}
