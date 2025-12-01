package com.emaksy.ghostnet.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")   // reagiert auf http://localhost:8080/
    public String home() {
        return "Hello from GhostNet Spring Boot 🚀";
    }

   //  @GetMapping("/hello")  // extra Endpoint: http://localhost:8080/hello
   //  public String hello() {
   //      return "Hello World 👋";
   //  }
}