package com.cap.project.demo.dto.request;

import com.cap.project.demo.domain.Department;
import com.cap.project.demo.domain.Expert;
import com.cap.project.demo.domain.enums.AttachmentType;
import com.cap.project.demo.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
@Setter
//binding for expert
public class ExpertJoinRequest {

    private String username; // 아이디
    private String password; // 비밀번호
    private String name;
    private String certificate_number;
    private String hospital_name;

    private Long department_id;
    private String career;
    private int age;

    private Map<AttachmentType, List<MultipartFile>> attachmentFiles = new ConcurrentHashMap<>();

    @Builder
    public ExpertJoinRequest(String username, String password, String name, String certificate_number,
                             String hospital_name, Long department_id ,String career, int age, Map<AttachmentType,
            List<MultipartFile>> attachmentFiles) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.certificate_number = certificate_number;
        this.hospital_name = hospital_name;
        this.department_id = department_id;
        this.career = career;
        this.age = age;
        this.attachmentFiles = attachmentFiles;

    }

    //create Expert Entity
    public Expert createExpertEntity(String encodedPassword , Department department){

        return Expert.builder()
                .loginId(username)
                .password(encodedPassword)
                .name(name)
                .certificate_number(certificate_number)
                .hospital_name(hospital_name)
                .department(department)
                .career(career)
                .department(department)
                .age(age)
                .role(Role.valueOf("ROLE_EXPERT"))
                .attachedFiles(new ArrayList<>())
                .build();

    }
}
