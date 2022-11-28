package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.*;
import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.*;
import com.comet.auctionfinder.repository.BoardRepository;
import com.comet.auctionfinder.repository.FileRepository;
import com.comet.auctionfinder.repository.MemberRepository;
import com.comet.auctionfinder.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository repository;
    private MemberRepository memberRepository;

    private ReplyRepository replyRepository;

    private FileRepository fileRepository;

    @Transactional
    public long writeBoard(BoardRequestDto dto, List<FileRequestDto> files, String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        dto.setMember(member);
        Board board = dto.toEntity();
        files.forEach((file) -> board.addFile(file.toEntity()));
        return repository.save(board).getId();
    }

    @Transactional(readOnly = true)
    public Page<BoardListDto> getBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending()); //10개씩 보이기
        return repository.findAll(pageable).map(BoardListDto::new);
    }

    @Transactional
    public List<BoardListDto> getAllBoardList(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        return repository.findAllByAuthor_Id(member.getId()).stream().map(BoardListDto::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<BoardListDto> getSearchList(String type, String content, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending()); //10개씩 보이기
        return switch (type) {
            case "제목" -> repository.findByTitleContains(pageable, content).map(BoardListDto::new);
            case "작성자" -> repository.findByAuthor_NickNameContains(pageable, content).map(BoardListDto::new);
            default -> repository.findByContentContains(pageable, content).map(BoardListDto::new);
        };
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
    public void editBoard(long id, BoardRequestDto dto, String userId, List<FileRequestDto> fileRequestDto) {
        Board board = repository.findById(id).orElseThrow();
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        if (board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN)
            board.update(dto.getTitle(), dto.getContent(), fileRequestDto);
    }

    @Transactional
    public long writeReply(ReplyRequestDto dto, String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow();
        Board board = repository.findById(dto.getId()).orElseThrow();
        dto.setMember(member);
        dto.setBoard(board);
        Reply reply = dto.toEntity();
        board.addReply(reply);
        return repository.save(board).getId(); //안해도 저장될듯 (transactional 에 의해 관리되므로)
    }

    @Transactional
    public void removeReply(long id, String userId) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        Board board = reply.getBoard();
        Member member = reply.getMember();
        if (member != null && (board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN)) {
            board.removeReply(reply);
            replyRepository.delete(reply);
        }
    }

    @Transactional
    public void removeImage(long id, String userId) {
        FileEntity entity = fileRepository.findById(id).orElseThrow();
        Board board = entity.getBoard();
        Member member = board.getAuthor();
        if (member != null && (board.getAuthor().getUserId().equals(userId) || member.getRole() == UserRole.ADMIN)) {
            board.removeFile(entity);
            fileRepository.delete(entity);
        }
    }

}
