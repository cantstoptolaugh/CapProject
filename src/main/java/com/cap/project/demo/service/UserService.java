package com.cap.project.demo.service;

import com.cap.project.demo.domain.User;
import com.cap.project.demo.domain.enums.Role;
import com.cap.project.demo.dto.request.UserJoinRequest;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // 원래는 User 사용하면 X , DTO로 사용해야한다. Join 하는 시점에 원래 role_type 필드에 권한의 종류를 명시하는 값을 넣어준다.
    // 실제로 Security에서 권한을 확인할때는 해당 User의 DB Column을 보고 권한을 판단하는 것은 아니다!
    @Transactional
    public UserResponse joinForPatient(UserJoinRequest user){

        String rawPw = user.getPassword();
        String encodePw = bCryptPasswordEncoder.encode(rawPw); //암호 부호화

        String loginId = user.getUsername(); // 로그인한 User의 아이디 get
        User encoder;


        encoder = User.builder()
                    .loginId(user.getUsername())
                    .password(encodePw)
                    .name(user.getName())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .age(user.getAge())
                    .role(Role.valueOf("ROLE_USER")).build();


        User entity = userRepository.save(encoder);

        UserResponse userLoginResponse = UserResponse.builder()
                .db_id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .age(entity.getAge())
                .role(entity.getRoleType()).build();


        return userLoginResponse;
    }

}
