package com.example.postproject.config;

import com.example.postproject.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인 진행
// 로그인에 성공하면 스프링 시큐리티의 세션에 UserDetails 타입의 Object로 저장

public class UserDetail implements UserDetails {

    /**
     * 우리가 직접 로그인 처리를 안해도 되는 대신 직접 지정해줘야 할 정보들
     *
     * POST /login에 대한 요청을 security가 가로채서 로그인 진행해주기 때문에 우리가 직접 @PostMapping("/login")을 만들지 않아도 됨
     */

    private final Member member;

    public UserDetail(Member member){
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getUserRole().toString()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
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
