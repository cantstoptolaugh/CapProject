package com.cap.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordCheckDto {

    private String password;

    private String passwordForSameCheck;

    @Builder
    public PasswordCheckDto(String password, String passwordForSameCheck) {
        this.password = password;
        this.passwordForSameCheck = passwordForSameCheck;
    }

}
