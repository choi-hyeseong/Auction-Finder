package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.role = member.getRole();
    }

    private long id;
    private String userId;
    private String nickName;
    private String email;
    private UserRole role;
}
