package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.dto.MemberResponseDto;
import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
public class MemberService {

    @NonNull
    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public MemberService(MemberRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public void createUser(MemberRequestDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole(UserRole.USER);
        log.info("member saved : {}", dto);
        repository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean isUserIDExist(String userId) {
        return repository.existsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public boolean isNickNameExist(String nickName) {
        return repository.existsByNickName(nickName);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(String userId) {
        return new MemberResponseDto(repository.findByUserId(userId).orElseThrow());
    }
}
