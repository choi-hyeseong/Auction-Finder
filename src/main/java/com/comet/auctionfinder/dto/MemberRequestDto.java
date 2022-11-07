package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.enums.UserRole;
import com.comet.auctionfinder.model.Member;
import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickName;
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;
    private UserRole role;


    public Member toEntity() {
        return Member.builder().email(email).userId(userId).password(password).role(role).nickName(nickName).build();
    }

}
