package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ReplyResponseDto {

    private long id;
    private String nickName;
    private String content;
    private LocalDateTime localDateTime;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.nickName =  reply.getMember().getNickName();
        this.content = reply.getContent();
        this.localDateTime = reply.getLocalDateTime();
    }

}
