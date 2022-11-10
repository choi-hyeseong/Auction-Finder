package com.comet.auctionfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestAttribute(required = false) String error, Model model) {
        if (error != null)
            model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
