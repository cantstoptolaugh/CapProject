package com.cap.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmotionController {

    @GetMapping("/face/emotion")
    public String emotion() {
        return "analysis/emotionanalysis";
    }

}
