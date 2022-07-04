package com.cap.project.demo.controller;

import com.cap.project.demo.dto.request.SurveyRequest;
import com.cap.project.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private IndexController indexController;

    @GetMapping("/survey")
    public String survey(Model model) {
        model.addAttribute("survey", new SurveyRequest());
        return "survey/surveyForm";
    }


    @PostMapping("/survey")
    public String survey(@ModelAttribute SurveyRequest surveyRequest , Model model){

        String[] arr = surveyService.getPoint(surveyRequest);

        String ManicDepression = arr[0];
        String Depression = arr[1];
        String PanicDisorder = arr[2];
        String Schizophrenia = arr[3];
        String Delusion = arr[4];
        String Sum = arr[5];

        // 정신적으로 문제 있어서 회원가입 가능한 경우 , 60점 이상시
        if(Integer.parseInt(Sum) >= 60)
        {
            return "redirect:/join/patient"; //회원가입 할 수 있는 페이지로 이동
        }

        // 회원가입 할 수 없음 (정신건강 크게 이상 없을 경우)
        model.addAttribute("ManicDepression", ManicDepression);
        model.addAttribute("Depression", Depression);
        model.addAttribute("PanicDisorder", PanicDisorder);
        model.addAttribute("Schizophrenia", Schizophrenia);
        model.addAttribute("Delusion", Delusion);

        if (Sum.equals("0")) {
            Sum="100" ;
            }

        model.addAttribute("Sum", Sum);

        return "survey/surveyFailed"; // 정신 건강이 좋아서 회원가입 불가 페이지로 이동
    }
}
