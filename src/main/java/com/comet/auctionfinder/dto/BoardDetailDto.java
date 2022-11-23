package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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

    public BoardDetailDto(Board board) {
        this.id = board.getId();
        this.nickName = board.getAuthor().getNickName();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.localDateTime = board.getLocalDateTime();
        this.viewCount = board.getViewCount();
    }
}