package com.comet.auctionfinder.nearby.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class NearbyController {

    @GetMapping("/nearby") //localhost:8080/nearby?
    public String nearby(@RequestParam String pro, @RequestParam String city, Model model) {
        model.addAttribute("pro", pro);
        model.addAttribute("city", city);
        return "nearby";
    }
}
