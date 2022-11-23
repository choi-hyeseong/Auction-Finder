package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.BoardDetailDto;
import com.comet.auctionfinder.dto.BoardListDto;
import com.comet.auctionfinder.dto.BoardRequestDto;
import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.Board;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.repository.BoardRepository;
import com.comet.auctionfinder.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository repository;
    private MemberRepository memberRepository;

    @Transactional
    public long writeBoard(BoardRequestDto dto, String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        dto.setMember(member);
        return repository.save(dto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public Page<BoardListDto> getBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10); //10개씩 보이기
        return repository.findAll(pageable).map(BoardListDto::new);
    }

    @Transactional
    public BoardDetailDto getBoardDetail(long id, String userId) {
        Board board = repository.findById(id).orElseThrow();
        //여기서부턴 throw 안되는 구간
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        board.setViewCount(board.getViewCount() + 1);
        BoardDetailDto detailDto = new BoardDetailDto(board);
        //어드민이거나 작성자거나
        detailDto.setEditable(board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN);
        return detailDto;
    }

    @Transactional
    public void deleteBoard(long id, String userId) {
        Board board = repository.findById(id).orElseThrow();
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        if (board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN)
            repository.delete(board);
    }

    @Transactional
    public void editBoard(long id, BoardRequestDto dto, String userId) {
        Board board = repository.findById(id).orElseThrow();
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        if (board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN)
            board.update(dto.getTitle(), dto.getContent());
    }

}
