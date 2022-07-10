package com.cap.project.demo.domain;

import com.cap.project.demo.domain.enums.Role;
import com.cap.project.demo.dto.request.UserUpdateDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_loginId" , columnDefinition = "varchar(255) NOT NULL UNIQUE ")
    private String loginId; // 사용자 로그인 아이디

    @Column(name = "user_password" , nullable = false)
    private String password;

    @Column(name = "user_name" , nullable = false )
    private String name; // 사용자 실제 이름 ex) 이수찬

    @Column(name="user_email" , nullable = false)
    private String email;

    @Column(name = "user_nickname" , columnDefinition = " varchar(255) NOT NULL UNIQUE ")
    private String nickname;

    @Column(name="user_age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10 , nullable = false)
    private Role roleType;

    @Column(name = "user_provider" )
    private String provider; // 소셜로그인 제공자 이름

    @Column(name = "user_providerId" )
    private String providerId; // 소셜로그인에서 제공 받은 sub(여러 자리의 숫자)

    // user can know list of boards
    @OneToMany(mappedBy = "user" )
    private List<Board> boards = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        // nickname이 없는 경우, loginId의 @ 앞까지 넣어줌
        this.nickname = this.nickname == null ?  this.email.substring(0, this.email.indexOf("@")) + "_" + this.provider : this.nickname;
    }

    public User() {
    }

    @Builder
    public User(String loginId, String password, String name, String nickname, int age ,Role role,
                String provider, String providerId , String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.roleType = role;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        this.name = userUpdateDto.getName();
        this.nickname = userUpdateDto.getNickname();
        this.age = userUpdateDto.getAge();
        this.email = userUpdateDto.getEmail();
    }
}
