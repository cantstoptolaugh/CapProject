package com.cap.project.demo.domain;

import com.cap.project.demo.domain.enums.Role;
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

    @Column(name = "user_nickname" , columnDefinition = " varchar(255) NOT NULL UNIQUE ")
    private String nickname;

    @Column(name="user_age", nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10 , nullable = false)
    private Role roleType;

    // user can know list of boards
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public User() {
    }

    @Builder
    public User(String loginId, String password, String name, String nickname, int age ,Role role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.roleType = role;
    }
}
