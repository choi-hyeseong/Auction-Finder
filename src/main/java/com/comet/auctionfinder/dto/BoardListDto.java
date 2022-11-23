package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BoardListDto {

    private long id;
    private String nickName;
    private String title;
    private LocalDateTime localDateTime;
    private int viewCount;

    public BoardListDto(Board board) {
        this.id = board.getId();
        this.nickName = board.getAuthor().getNickName();
        this.title = board.getTitle();
        this.localDateTime = board.getLocalDateTime();
        this.viewCount = board.getViewCount();
    }

}
