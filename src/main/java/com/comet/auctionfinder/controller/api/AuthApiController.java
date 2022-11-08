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
        service.createUser(dto);
        return new ResponseEntity<>(new ApiResponse("success!", true),HttpStatus.OK);
    }
}

