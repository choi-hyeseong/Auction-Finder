package com.comet.auctionfinder.service;

import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {

    private MemberRepository repository;
    //저장 및 암호화 => MemberService
    //로그인 조회 => LoginService
    //원인 => BCrypt 빈

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUserId(username).orElseThrow();
        return new User(member.getUserId(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getRole())));
    }
}
