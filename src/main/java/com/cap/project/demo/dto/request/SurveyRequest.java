package com.cap.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class SurveyRequest {

    private String forSure1;
    private String forSure2;
    private String forSure3;
    private String forSure4;
    private String forSure5;
    private String forSure6;
    private String forSure7;
    private String forSure8;
    private String forSure9;
    private String forSure10;

    @Builder
    public SurveyRequest(String forSure1, String forSure2, String forSure3, String forSure4, String forSure5, String forSure6, String forSure7, String forSure8, String forSure9, String forSure10) {
        this.forSure1 = forSure1;
        this.forSure2 = forSure2;
        this.forSure3 = forSure3;
        this.forSure4 = forSure4;
        this.forSure5 = forSure5;
        this.forSure6 = forSure6;
        this.forSure7 = forSure7;
        this.forSure8 = forSure8;
        this.forSure9 = forSure9;
        this.forSure10 = forSure10;
    }
}
