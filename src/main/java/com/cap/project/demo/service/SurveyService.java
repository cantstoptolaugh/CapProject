package com.cap.project.demo.service;

import com.cap.project.demo.dto.request.SurveyRequest;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    public String[] getPoint(SurveyRequest surveyRequest) {

        // 10개의 질문이 있고 10 , 20 30 40 50 으로 들어온다.
        // 2개의 질문은 하나의 질병을 대변한다. 2개의 질문에서 최대 득점 가능한 점수는 100점이다.
        // 2개의 질문에 대해서 총점 60점 이상이면 질병 환자로 간주


        int check1 = Integer.parseInt(surveyRequest.getForSure1());
        int check2 = Integer.parseInt(surveyRequest.getForSure2());

        int check3 = Integer.parseInt(surveyRequest.getForSure3());
        int check4 = Integer.parseInt(surveyRequest.getForSure4());

        int check5 = Integer.parseInt(surveyRequest.getForSure5());
        int check6 = Integer.parseInt(surveyRequest.getForSure6());

        int check7 = Integer.parseInt(surveyRequest.getForSure7());
        int check8 = Integer.parseInt(surveyRequest.getForSure8());

        int check9 = Integer.parseInt(surveyRequest.getForSure9());
        int check10 = Integer.parseInt(surveyRequest.getForSure10());

        int ManicDepression = 0;
        int Depression = 0;
        int PanicDisorder = 0;
        int Schizophrenia = 0;
        int Delusion = 0;

        //조울증 판독 10 (2개의 질문에 대한 최대 득점 가능한 점수는 100점이다. 60점 미만이면 정상 범위)
        if (check1 + check2 < 60)
        {
            ManicDepression = 10;
        }else {
            ManicDepression = 20;
        }

        //우울증 판독 10
        if (check3 + check4 < 60)
        {
            Depression = 10;
        }else
        {
            Depression = 20;
        }

        //공황장애 판독 10
        if (check5 + check6 < 60)
        {
            PanicDisorder = 10;
        }else
        {
            PanicDisorder = 20;
        }

        //조현증 판독 10
        if (check7 + check8 < 60)
        {
            Schizophrenia = 10;
        }else
        {
            Schizophrenia = 20;
        }

        //망상장애 판독 10
        if (check9 + check10 < 60)
        {
            Delusion = 10;
        }else
        {
            Delusion = 20;
        }

        int sum = ManicDepression + Depression + PanicDisorder + Schizophrenia + Delusion;

        String[] arr = {String.valueOf(ManicDepression), String.valueOf(Depression), String.valueOf(PanicDisorder),
                String.valueOf(Schizophrenia), String.valueOf(Delusion) , String.valueOf(sum)};

        return arr;
    }


}
