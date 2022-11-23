package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Board;
import com.comet.auctionfinder.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class BoardRequestDto {

    @Nullable
    private Member member;

    @NotBlank(message = "제목은 비어있어선 안됩니다.")
    private String title;
    @NotBlank(message = "내용은 비어있어선 안됩니다.")
    private String content;

    public Board toEntity() {
        return Board.builder().author(member).title(title).content(content).viewCount(0).build();
    }
}
