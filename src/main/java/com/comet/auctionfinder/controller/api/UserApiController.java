package com.comet.auctionfinder.controller.api;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.dto.MemberResponseDto;
import com.comet.auctionfinder.dto.MemberUpdateDto;
import com.comet.auctionfinder.response.ApiResponse;
import com.comet.auctionfinder.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.comet.auctionfinder.util.SecurityUtil.checkAjax;

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
        if (!checkAjax(auth)) {
            MemberResponseDto dto = service.getMember(auth.getUsername());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        else
            return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    public ResponseEntity<ApiResponse> updateUser(@Valid @AuthenticationPrincipal UserDetails auth, @RequestBody MemberUpdateDto dto) {
        if (!checkAjax(auth)) {
            ApiResponse response = service.updateMember(dto, auth.getUsername());
            if (response.isSuccess())
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else
            return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public ResponseEntity<Integer> removeUser(@Valid @AuthenticationPrincipal UserDetails auth, HttpServletRequest request) {
        if (!checkAjax(auth)) {
            service.deleteMember(auth.getUsername());
            request.getSession().invalidate(); //제거
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return ResponseEntity.badRequest().build();
    }
}

