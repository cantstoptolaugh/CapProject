package com.cap.project.demo.dto.response;

import com.cap.project.demo.domain.Attachment;
import com.cap.project.demo.domain.Department;
import com.cap.project.demo.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
// response dto for expert entity
public class ExpertResponse implements Serializable {

    private Long db_id;

    private String loginId; // 사용자 로그인 아이디

    private String password;

    private String certificate_number;

    private String hospital_name;

    private String career;

    private Department department;

    private String name;

    private int age;

    private Role role;

    private List<Attachment> attachedFiles;

    @Builder
    public ExpertResponse(Long db_id, String loginId, String password, String certificate_number,
                          String hospital_name, String career, String name, Department department, int age,
                          Role role , List<Attachment> attachedFiles) {
        this.db_id = db_id;
        this.loginId = loginId;
        this.password = password;
        this.certificate_number = certificate_number;
        this.department = department;
        this.hospital_name = hospital_name;
        this.career = career;
        this.name = name;
        this.age = age;
        this.role = role;
        this.attachedFiles = attachedFiles;

    }

}
