package com.emaksy.ghostnet.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecoveriesController {

  @GetMapping("/recoveries")
  public String report() {
    return "pages/recovery-page";
  }
}
