package com.comet.auctionfinder.controller.api;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.dto.MemberResponseDto;
import com.comet.auctionfinder.response.ApiResponse;
import com.comet.auctionfinder.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController()
@AllArgsConstructor
public class UserApiController {

    private MemberService service;

    @PostMapping("/register") //requestbody == methodargumentnotvalid
    public ResponseEntity<ApiResponse> createRegister(@Valid @RequestBody MemberRequestDto dto) {
        service.createUser(dto);
        return new ResponseEntity<>(new ApiResponse("success!", true),HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<MemberResponseDto> getUser(@AuthenticationPrincipal UserDetails auth) {
        if (auth != null) {
            MemberResponseDto dto = service.getMember(auth.getUsername());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        else
            return ResponseEntity.badRequest().build();
    }
}

