package com.comet.auctionfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/") //main으로 리다이렉트
    public String none() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/map") //localhost:8080/map
    public String map() {
        return "map";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @GetMapping("/board/search")
    public String search() {
        return "search";
    }


    @GetMapping("/board/write")
    public String write() {
        return "write";
    }

    @GetMapping("/board/detail")
    public String boardDetail() {
        return "boardDetail";
    }

    @GetMapping("/board/edit")
    public String boardEdit() {
        return "edit";
    }
}
