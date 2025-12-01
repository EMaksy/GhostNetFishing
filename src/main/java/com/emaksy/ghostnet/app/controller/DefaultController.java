package com.emaksy.ghostnet.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller 
public class DefaultController {

    @GetMapping("/")
    public String home() {
         return "redirect:/index.html";
    }

}
