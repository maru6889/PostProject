package com.example.postproject.config;

import com.example.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/member/login", "/member/join", "/css/**", "/js/**").permitAll() //공개된 페이지

                        //게시글 관련 접근 권한 설정
                        .requestMatchers(HttpMethod.GET, "/post/**").permitAll() //게시글 조회는 누구나 가능
                        .requestMatchers(HttpMethod.POST, "/post/**").authenticated() //글 작성 수정 삭제는 인증 필요

                        //댓글 관련 접근 권한 설정
                        .requestMatchers(HttpMethod.POST, "/comment/**").authenticated() //댓글 작성 수정 삭제는 인증 필요


                        .requestMatchers("/member/**").authenticated() //인증 필요
                        .anyRequest().permitAll() // 나머지 요청은 모두 허용
                )
                .formLogin(login -> login
                        .loginPage("/member/login") //사용자가 정의한 로그인 페이지
                        .defaultSuccessUrl("/") //로그인 성공 시 이동할 기본 페이지
                        .failureUrl("/member/login?error=true") //로그인 실패 시 이동할 페이지
                        .usernameParameter("loginId") // 로그인 폼에서 사용자 이이디 필드
                        .passwordParameter("password") //로그인 폼에서 사용자 비밀번호 필드
                )
                .logout(logout  -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/") //로그아웃 성공 후 이동할 페이지
                        .invalidateHttpSession(true) //세션 무효화
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
