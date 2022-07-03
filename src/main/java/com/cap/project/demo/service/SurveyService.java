package com.cap.project.demo.service;

import com.cap.project.demo.dto.request.SurveyRequest;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    public String[] getPoint(SurveyRequest surveyRequest) {

        String check1 = surveyRequest.getForSure1();
        String check2 = surveyRequest.getForSure2();
        String check3 = surveyRequest.getForSure3();
        String check4 = surveyRequest.getForSure4();
        String check5 = surveyRequest.getForSure5();
        String check6 = surveyRequest.getForSure6();
        String check7 = surveyRequest.getForSure7();
        String check8 = surveyRequest.getForSure8();
        String check9 = surveyRequest.getForSure9();
        String check10 = surveyRequest.getForSure10();

        int ManicDepression = 0;
        int Depression = 0;
        int PanicDisorder = 0;
        int Schizophrenia = 0;
        int Delusion = 0;

        //조울증 판독 10
        if (check1.equals("yes")||check2.equals("yes"))
        {
            ManicDepression = 10;
        }
        //조울증 판독 20
        if (check1.equals("yes")&&check2.equals("yes"))
        {
            ManicDepression = 20;
        }

        //우울증 판독 10
        if (check3.equals("yes")||check4.equals("yes"))
        {
            Depression = 10;
        }
        //우울증 판독 20
        if (check3.equals("yes")&&check4.equals("yes"))
        {
            Depression = 20;
        }

        //공황장애 판독 10
        if (check5.equals("yes")||check6.equals("yes"))
        {
            PanicDisorder = 10;
        }
        //공황장애 판독 20
        if (check5.equals("yes")&&check6.equals("yes"))
        {
            PanicDisorder = 20;
        }

        //조현증 판독 10
        if (check7.equals("yes")||check8.equals("yes"))
        {
            Schizophrenia = 10;
        }
        //조현증 판독 20
        if (check7.equals("yes")&&check8.equals("yes"))
        {
            Schizophrenia = 20;
        }

        //망상장애 판독 10
        if (check9.equals("yes")||check10.equals("yes"))
        {
            Delusion = 10;
        }
        //망상장애 판독 20
        if (check9.equals("yes")&&check10.equals("yes"))
        {
            Delusion = 20;
        }

        int sum = ManicDepression + Depression + PanicDisorder + Schizophrenia + Delusion;

        String[] arr = {String.valueOf(ManicDepression), String.valueOf(Depression), String.valueOf(PanicDisorder),
                String.valueOf(Schizophrenia), String.valueOf(Delusion) , String.valueOf(sum)};

        return arr;
    }


}
