package com.comet.auctionfinder.controller.api;


import com.comet.auctionfinder.dto.BoardDetailDto;
import com.comet.auctionfinder.dto.BoardListDto;
import com.comet.auctionfinder.dto.BoardRequestDto;
import com.comet.auctionfinder.response.ApiResponse;
import com.comet.auctionfinder.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.comet.auctionfinder.util.SecurityUtil.checkAjax;

@RestController
@RequestMapping("/api/board")
@AllArgsConstructor
public class BoardApiController {

    private BoardService service;

    //폼 방식이니 modelattribute
    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> writeBoard(@Valid @ModelAttribute BoardRequestDto requestDto, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details)) {
            ApiResponse response = new ApiResponse("로그인 되어있지 않습니다.", false);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            long result = service.writeBoard(requestDto, details.getUsername());
            ApiResponse response = new ApiResponse("게시글 작성 성공 : " + result, true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BoardListDto>> getBoardList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(service.getBoardList(page), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BoardDetailDto> getBoardDetail(@PathVariable(value = "id") long id, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details))
            return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(service.getBoardDetail(id, details.getUsername()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> removeBoard(@PathVariable(value = "id") long id, @AuthenticationPrincipal UserDetails details) {
       if (checkAjax(details))
           return new ResponseEntity<>(new ApiResponse("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
       else {
           service.deleteBoard(id, details.getUsername());
           return new ResponseEntity<>(new ApiResponse("게시글이 제거되었습니다.", true), HttpStatus.OK);
       }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> editBoard(@Valid @PathVariable(value = "id") long id, @ModelAttribute BoardRequestDto dto, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details))
            return new ResponseEntity<>(new ApiResponse("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
        else {
            service.editBoard(id, dto, details.getUsername());
            return new ResponseEntity<>(new ApiResponse("게시글이 수정되었습니다.", true), HttpStatus.OK);
        }
    }
}
