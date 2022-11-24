package com.comet.auctionfinder.controller.api;


import com.comet.auctionfinder.component.FileHandler;
import com.comet.auctionfinder.dto.BoardDetailDto;
import com.comet.auctionfinder.dto.BoardListDto;
import com.comet.auctionfinder.dto.BoardRequestDto;
import com.comet.auctionfinder.dto.ReplyRequestDto;
import com.comet.auctionfinder.model.FileRequestDto;
import com.comet.auctionfinder.model.FileResponseDto;
import com.comet.auctionfinder.response.ApiResponse;
import com.comet.auctionfinder.service.BoardService;
import com.comet.auctionfinder.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static com.comet.auctionfinder.util.SecurityUtil.checkAjax;

@RestController
@RequestMapping("/api/board")
@AllArgsConstructor
public class BoardApiController {

    private BoardService service;
    private FileHandler handler;
    private ImageService imageService;

    //폼 방식이니 modelattribute
    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> writeBoard(@Valid @ModelAttribute BoardRequestDto requestDto, @RequestParam(name = "file", required = false) List<MultipartFile> files, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details)) {
            ApiResponse response = new ApiResponse("로그인 되어있지 않습니다.", false);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            List<FileRequestDto> dtoList = handler.saveFile(files);
            long result = service.writeBoard(requestDto, dtoList, details.getUsername());
            ApiResponse response = new ApiResponse("게시글 작성 성공 : " + result, true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BoardListDto>> getBoardList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(service.getBoardList(page), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BoardListDto>> getBoardSearchList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam String type, @RequestParam String content) {
        return new ResponseEntity<>(service.getSearchList(type, content, page), HttpStatus.OK);
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
    public ResponseEntity<ApiResponse> editBoard(@Valid @PathVariable(value = "id") long id, @RequestParam(name = "file", required = false) List<MultipartFile> files, @ModelAttribute BoardRequestDto dto, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details))
            return new ResponseEntity<>(new ApiResponse("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
        else {
            List<FileRequestDto> dtoList = handler.saveFile(files);
            service.editBoard(id, dto, details.getUsername(), dtoList);
            return new ResponseEntity<>(new ApiResponse("게시글이 수정되었습니다.", true), HttpStatus.OK);
        }
    }

    @PostMapping("/reply/write")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> writeReply(@Valid ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details)) {
            ApiResponse response = new ApiResponse("로그인 되어있지 않습니다.", false);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            long result = service.writeReply(requestDto, details.getUsername());
            ApiResponse response = new ApiResponse("댓글 작성 성공 - 게시판 ID : " + result, true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/reply/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> deleteReply(@PathVariable(value = "id") long id, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details)) {
            ApiResponse response = new ApiResponse("로그인 되어있지 않습니다.", false);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            service.removeReply(id, details.getUsername());
            ApiResponse response = new ApiResponse("댓글 삭제 성공", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> image(@PathVariable(value = "id") long id) {
        try {
            Optional<FileResponseDto> dto = imageService.getImage(id);
            if (dto.isPresent()) {
                FileResponseDto responseDto = dto.get();
                Resource image = handler.fileDtoToResource(responseDto);
                if (!image.exists())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-type", Files.probeContentType(handler.getFilePath(responseDto)));
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/image/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable(value = "id") long id, @AuthenticationPrincipal UserDetails details) {
        if (checkAjax(details)) {
            ApiResponse response = new ApiResponse("로그인 되어있지 않습니다.", false);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            service.removeImage(id, details.getUsername());
            ApiResponse response = new ApiResponse("이미지 삭제 성공", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
