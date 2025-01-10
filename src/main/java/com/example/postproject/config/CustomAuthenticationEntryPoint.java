package com.example.postproject.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter pw = response.getWriter();
        pw.println("<script>alert('로그인이 필요한 기능입니다.'); location.href='/member/login';</script>"); //메세지 출력 후 지정한 url로 이동
        pw.flush();
    }
}
