package com.cap.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {

    private String name;
    private String email;
    private int age;
    private String nickname;

    @Builder
    public UserUpdateDto(String name, String email, int age, String nickname) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.nickname = nickname;
    }


}
