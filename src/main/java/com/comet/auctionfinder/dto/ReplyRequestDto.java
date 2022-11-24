package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Board;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class ReplyRequestDto {

    @NotNull(message = "id값은 비어있어선 안됩니다.")
    private long id;
    @NotEmpty(message = "내용은 비어있어선 안됩니다.")
    private String content;
    @Nullable
    private Member member;
    @Nullable
    private Board board;

    public Reply toEntity() {
        return Reply.builder().board(board).member(member).content(content).build();
    }
}
