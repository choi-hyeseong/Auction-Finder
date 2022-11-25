package com.comet.auctionfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDto {

    @NotEmpty(message = "닉네임은 비어있어선 안됩니다.")
    private String nickName;
    @Email(message = "이메일 형식이 와야합니다.")
    private String email;

}
