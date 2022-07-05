package com.cap.project.demo.config.Oauth2;


import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.domain.User;
import com.cap.project.demo.domain.enums.Role;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.UserRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService  extends DefaultOAuth2UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){

        OAuth2User oAuth2 = super.loadUser(userRequest);

        // 회원가입을 강제로 진행해볼 예정 (다만, 구글과 페이스북에 제공하는게 조금씩 다르기 때문에 유지보수 입장에서 인터페이스 활용)
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2.getAttributes().get("response"));
        }else{
            throw new IllegalArgumentException("알수없는 OAuth2 클라이언트 정보 : " + userRequest.getClientRegistration().getRegistrationId());
        }



        // 실제 DB에 저장할 User entity에 대한 정보를 oAuth2User에서 구성해서 가져와야 한다.
        String provider = oAuth2UserInfo.getProvider();// google
        String providerId = oAuth2UserInfo.getProviderId();
        String loginId = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("demo");
        String email = oAuth2UserInfo.getEmail(); // email
        String name = oAuth2UserInfo.getName(); // name
        Role role = Role.valueOf("ROLE_USER");

        User user = userRepository.findByLoginId(loginId).orElse(null);

        UserResponse userResponse;

        if(user == null){
            User entity = User.builder()
                    .loginId(loginId)
                    .password(password)
                    .name(name)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(entity);

            userResponse = UserResponse.builder()
                    .db_id(entity.getId())
                    .loginId(entity.getLoginId())
                    .password(entity.getPassword())
                    .name(entity.getName())
                    .nickname(entity.getNickname())
                    .age(entity.getAge())
                    .role(entity.getRoleType())
                    .provider(entity.getProvider())
                    .providerId(entity.getProviderId())
                    .build();
        }else {
            userResponse = UserResponse.builder()
                    .db_id(user.getId())
                    .loginId(user.getLoginId())
                    .password(user.getPassword())
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .age(user.getAge())
                    .role(user.getRoleType())
                    .provider(user.getProvider())
                    .providerId(user.getProviderId())
                    .build();
        }

        return new PrincipalDetails(userResponse , oAuth2.getAttributes());
    }

}
