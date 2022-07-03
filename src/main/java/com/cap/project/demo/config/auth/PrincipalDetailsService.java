package com.cap.project.demo.config.auth;


import com.cap.project.demo.domain.Expert;
import com.cap.project.demo.domain.User;
import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.ExpertRepository;
import com.cap.project.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;


@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpertRepository expertRepository;


    @Override // 해당 username은 사용자 고유한 Id 값이다 unique로 설정된!!!
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {


        String[] split = input.split(":");
        String username = split[0];
        String userType = split[1];

        if(userType.equals("patient")){
            User userEntity = userRepository.findByLoginId(username).orElse(null);

            if(userEntity != null) {

                UserResponse dto = UserResponse.builder()
                        .db_id(userEntity.getId())
                        .name(userEntity.getName())
                        .nickname(userEntity.getNickname())
                        .loginId(userEntity.getLoginId())
                        .password(userEntity.getPassword())
                        .role(userEntity.getRoleType())
                        .build();


                return new PrincipalDetails(dto);
            }


        }else{
            Expert expertEntity = expertRepository.findByLoginId(username).orElse(null);

            if(expertEntity != null) {

                // builder for expert
                ExpertResponse dto = ExpertResponse.builder()
                        .db_id(expertEntity.getId())
                        .loginId(expertEntity.getLoginId())
                        .password(expertEntity.getPassword())
                        .certificate_number(expertEntity.getCertificate_number())
                        .hospital_name(expertEntity.getHospital_name())
                        .career(expertEntity.getCareer())
                        .name(expertEntity.getName())
                        .age(expertEntity.getAge())
                        .role(expertEntity.getRoleType()).build();

                return new PrincipalDetailsForExpert(dto);
            }

        }


        return null;

    }
}
