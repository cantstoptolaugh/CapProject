package com.cap.project.demo.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "experts")
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_id")
    private Long id;

    //login id
    @Column(name = "expert_loginId" , columnDefinition = "varchar(255) NOT NULL UNIQUE ")
    private String loginId; // 심리 상담 전문가 로그인 아이디

    //password
    @Column(name = "expert_password" , nullable = false)
    private String password;

    //certificate_number
    @Column(name = "expert_certificate_number" , nullable = false)
    private String certificate_number;

    // join with department table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id" , nullable = false , foreignKey = @ForeignKey(name = "fk_expert_department"))
    private Department department;

    //hospital_name
    @Column(name = "expert_hospital_name" , nullable = false)
    private String hospital_name;

    //career
    @Column(name = "expert_career" , nullable = false)
    private String career;

    @Column(name = "expert_name" , nullable = false)
    private String name; // 심리 상담 전문가 이름

    @Column(name="expert_age", nullable = false)
    private int age;

    public Expert() {
    }

    @Builder
    public Expert(String loginId, String password, String certificate_number, String hospital_name, String career, String name, int age) {
        this.loginId = loginId;
        this.password = password;
        this.certificate_number = certificate_number;
        this.hospital_name = hospital_name;
        this.career = career;
        this.name = name;
        this.age = age;
    }
}
