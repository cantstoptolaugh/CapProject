package com.cap.project.demo.config.auth;


import com.cap.project.demo.dto.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class PrincipalDetails implements UserDetails {


    private UserResponse user; //콤포지션


    public PrincipalDetails(UserResponse user) {
        this.user = user;
    }

    public UserResponse getUser() {

        UserResponse userDto = UserResponse.builder()
                .db_id(user.getDb_id())
                .name(user.getName())
                .nickname(user.getNickname())
                .age(user.getAge())
                .role(user.getRole()).build();

        return userDto;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return user.getRole().getAuthority();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getLoginId();
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
