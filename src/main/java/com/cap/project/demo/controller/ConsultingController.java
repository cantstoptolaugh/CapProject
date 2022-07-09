package com.cap.project.demo.controller;

import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ConsultingController {

    @Autowired
    private ExpertService expertService;


    @GetMapping("/consulting")
    public String consulting(Model model) {

        List<ExpertResponse> expertResponses = expertService.searchAllExperts();
        model.addAttribute("experts", expertResponses);

        return "consulting/expertList";
    }

}
