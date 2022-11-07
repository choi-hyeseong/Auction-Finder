package com.comet.auctionfinder.controller.api;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.response.ApiResponse;
import com.comet.auctionfinder.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController()
@AllArgsConstructor
public class AuthApiController {

    private MemberService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createRegister(@Valid @RequestBody MemberRequestDto dto) {
        String message;
        boolean success;
        boolean isNickExist = service.isNickNameExist(dto.getNickName());
        boolean isUserIdExist = service.isUserIDExist(dto.getUserId());
        if (isNickExist || isUserIdExist) {
            success = false;
            if (service.isNickNameExist(dto.getNickName()))
                message = "이미 존재하는 닉네임입니다.";
            else
                message = "이미 존재하는 아이디입니다.";
        }
        else {
            message = "성공!";
            success = true;
            service.createUser(dto);
        }
        return new ResponseEntity<>(new ApiResponse(message, success), success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}

