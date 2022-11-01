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

    @GetMapping("/nearby") //localhost:8080/nearby?
    public String nearby(@RequestParam String pro, @RequestParam String city, Model model) {
        model.addAttribute("pro", pro);
        model.addAttribute("city", city);
        return "nearby";
    }

}
