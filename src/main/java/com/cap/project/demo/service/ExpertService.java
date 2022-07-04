package com.cap.project.demo.service;

import com.cap.project.demo.domain.Attachment;
import com.cap.project.demo.domain.Department;
import com.cap.project.demo.domain.Expert;
import com.cap.project.demo.dto.request.ExpertJoinRequest;
import com.cap.project.demo.repository.DepartmentRepository;
import com.cap.project.demo.repository.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.io.IOException;
import java.util.List;

@Service
public class ExpertService {

    @Autowired
    private AttachmentServiceImpl attachmentServiceImpl;

    @Autowired
    private ExpertRepository expertRepository;

    // encoding expert password
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Transactional
    public Expert joinForExpert(ExpertJoinRequest expertJoinRequest) throws IOException {

        List<Attachment> attachments = attachmentServiceImpl.saveAttachments(expertJoinRequest.getAttachmentFiles());

        // get expertJoinRequest's password to do bcrypt encoding
        String password = expertJoinRequest.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // 임의로 department repository를 통해서 데이터를 가져온다.
        Department department = departmentRepository.findById(expertJoinRequest.getDepartment_id()).get();
        Expert expertEntity = expertJoinRequest.createExpertEntity(encodedPassword , department);

        attachments.stream().forEach(e->e.addExpert(expertEntity));


        return expertRepository.save(expertEntity);

    }

}
