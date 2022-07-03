package com.cap.project.demo.dto.request;

import com.cap.project.demo.domain.enums.AttachmentType;
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
public class ExpertJoinRequestForm {


    private String username; // 아이디
    private String password; // 비밀번호
    private String name;
    private String certificate_number;
    private String hospital_name;
    private String career;
    private int age;

    private List<MultipartFile> imageFiles;

    @Builder
    public ExpertJoinRequestForm(String username, String password, String name, String certificate_number, String hospital_name,
                             String career, int age , List<MultipartFile> imageFiles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.certificate_number = certificate_number;
        this.hospital_name = hospital_name;
        this.career = career;
        this.age = age;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
    }


    public ExpertJoinRequest createExpertJoinRequest() {

        Map<AttachmentType, List<MultipartFile>> attachments = getAttachmentTypeListMap();

        return ExpertJoinRequest.builder()
                .username(username)
                .password(password)
                .name(name)
                .certificate_number(certificate_number)
                .hospital_name(hospital_name)
                .career(career)
                .age(age)
                .attachmentFiles(attachments)
                .build();
    }



    private Map<AttachmentType, List<MultipartFile>> getAttachmentTypeListMap() {

        Map<AttachmentType, List<MultipartFile>> attachments = new ConcurrentHashMap<>();

        attachments.put(AttachmentType.IMAGE, imageFiles);

        return attachments;
    }

}
