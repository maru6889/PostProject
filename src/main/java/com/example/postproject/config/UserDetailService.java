package com.example.postproject.config;

import com.example.postproject.domain.Member;
import com.example.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByLoginId(username);

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.: " + username);
        }

        return new UserDetail(member);
    }
}
