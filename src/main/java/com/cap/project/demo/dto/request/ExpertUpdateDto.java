package com.cap.project.demo.dto.request;

import com.cap.project.demo.domain.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpertUpdateDto {

    private String certificate_number;

    private String hospital_name;

    private String career;

    private Long department_id;

    private String name;

    private int age;


    @Builder
    public ExpertUpdateDto(String certificate_number, String hospital_name, String career, Long department_id, String name, int age) {
        this.certificate_number = certificate_number;
        this.hospital_name = hospital_name;
        this.career = career;
        this.department_id = department_id;
        this.name = name;
        this.age = age;
    }

}
