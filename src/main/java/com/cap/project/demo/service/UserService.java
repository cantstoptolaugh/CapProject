package com.cap.project.demo.service;

import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.domain.User;
import com.cap.project.demo.domain.enums.Role;
import com.cap.project.demo.dto.request.PasswordCheckDto;
import com.cap.project.demo.dto.request.UserJoinRequest;
import com.cap.project.demo.dto.request.UserUpdateDto;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserResponse getUserInfo(Long db_id) {

        User user = userRepository.findById(db_id).orElse(null);

        if(user != null) {
            return UserResponse.builder()
                    .db_id(user.getId())
                    .provider(user.getProvider())
                    .name(user.getName())
                    .age(user.getAge())
                    .email(user.getEmail())
                    .role(user.getRoleType())
                    .nickname(user.getNickname())
                    .build();
        }

        return null;

    }

    public String checkPasswordForWithdrawal(PasswordCheckDto passwordCheckDto, PrincipalDetails principal) {

        String rawPw = passwordCheckDto.getPassword();

        // need to check encodePw with principal.getPassword()
        if(bCryptPasswordEncoder.matches(rawPw, principal.getPassword())) {

            // if password is correct, need to withdraw user
            userRepository.deleteById(principal.getUser().getDb_id());

            // need to clear security context
            SecurityContextHolder.clearContext();

            return "success";
        }else{
            return "fail";
        }

    }

    public String updateUserInfo(UserUpdateDto userUpdateDto , Long db_id) {

            User user = userRepository.findById(db_id).orElse(null);

            //UserUpdateDto에는 이미 데이터가 담겨있다.
            if(user != null){
                user.updateUser(userUpdateDto);
            }else{
                return "false";
            }

            userRepository.save(user);

        return "success";
    }

    public String findUsername(String name, String email) {

        User user = userRepository.findByNameAndEmail(name, email);

        if(user != null) {
            return user.getLoginId();
        }

        return "fail_find_username";
    }

    public String findPassword(String name, String id, String email) {

        User user = userRepository.findByNameAndLoginIdAndEmail(name, id, email);

        if(user != null) {
            return user.getPassword();
        }

        return "fail_find_password";
    }

    @Transactional
    public void updatePassword(String loginId, String password) {

        String encodePw = bCryptPasswordEncoder.encode(password);

        userRepository.findByLoginId(loginId).ifPresent(user -> {
            user.updatePassword(encodePw);
            userRepository.save(user);
        });
    }
}
