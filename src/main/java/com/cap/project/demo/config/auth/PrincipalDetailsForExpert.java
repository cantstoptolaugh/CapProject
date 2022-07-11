package com.cap.project.demo.config.auth;

import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetailsForExpert implements UserDetails {

    private ExpertResponse expert; //콤포지션


    public PrincipalDetailsForExpert(ExpertResponse expert) {
        this.expert = expert;
    }

    public ExpertResponse getExpert() {

        ExpertResponse expertDto = ExpertResponse.builder()
                .db_id(expert.getDb_id())
                .name(expert.getName())
                .age(expert.getAge())
                .certificate_number(expert.getCertificate_number())
                .role(expert.getRole()).build();

        return expertDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return expert.getRole().getAuthority();
            }
        });

        return collection;

    }

    @Override
    public String getPassword() {
        return expert.getPassword();
    }

    @Override
    public String getUsername() {
        return expert.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}