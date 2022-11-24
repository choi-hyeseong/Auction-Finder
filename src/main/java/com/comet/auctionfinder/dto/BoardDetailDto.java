package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Board;
import com.comet.auctionfinder.model.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BoardDetailDto {

    private long id;
    private String nickName;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private int viewCount;

    private boolean isEditable;

    private List<ReplyResponseDto> replyList;

    private List<FileResponseDto> fileList;

    public BoardDetailDto(Board board) {
        this.id = board.getId();
        this.nickName = board.getAuthor().getNickName();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.localDateTime = board.getLocalDateTime();
        this.viewCount = board.getViewCount();
        this.fileList = board.getFileList().stream().map(FileResponseDto::new).toList();
        this.replyList = board.getReplyList().stream().map(ReplyResponseDto::new).toList();
    }
}