package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.dto.MemberResponseDto;
import com.comet.auctionfinder.dto.MemberUpdateDto;
import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.Board;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.repository.BoardRepository;
import com.comet.auctionfinder.repository.MemberRepository;
import com.comet.auctionfinder.repository.ReplyRepository;
import com.comet.auctionfinder.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @NonNull
    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Autowired
    public MemberService(MemberRepository repository, PasswordEncoder encoder, BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
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

    @Transactional
    public ApiResponse updateMember(MemberUpdateDto dto, String userId) {
        Member member = repository.findByUserId(userId).orElseThrow();
        Optional<Member> nickMember = repository.findByNickName(dto.getNickName());
        if (nickMember.isPresent() && !nickMember.get().getUserId().equals(userId))
            return new ApiResponse("닉네임이 이미 존재합니다.", false);
        else {
            member.update(dto);
            return new ApiResponse("업데이트 완료", true);
        }
    }

    @Transactional
    public void deleteMember(String userId) {
        Member member = repository.findByUserId(userId).orElseThrow();
        //지울시 하트는 삭제됨
        List<Board> board = boardRepository.findAllByAuthor_Id(member.getId());
        //멤버랑 보드 삭제시 지워지는거
        //멤버 => heart
        //보드 => reply, file
        //다른 게시글에 쓴 댓글도 있어서 그것도 지워야됨..
        replyRepository.deleteAllByMember_Id(member.getId());
        boardRepository.deleteAll(board);
        repository.delete(member);
    }
}
