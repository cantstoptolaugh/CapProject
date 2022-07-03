package com.cap.project.demo.dto.response;

import com.cap.project.demo.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserResponse implements Serializable {

    private Long db_id;

    private String loginId; // 사용자 로그인 아이디

    private String password;
    private String name;
    private String nickname;

    private int age;
    private Role role;



    @Builder
    public UserResponse(Long db_id, String loginId, String password, String name, String nickname,
                         int age , Role role) {
        this.db_id = db_id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.role = role;

    }
}
