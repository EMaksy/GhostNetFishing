package com.emaksy.ghostnet.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportGhostNetController {
  @GetMapping("/report")
  public String report() {
    return "pages/report-page";
  }

  @PostMapping("/report")
  public String submitReport(
      @RequestParam String latitude,
      @RequestParam String longitude,
      @RequestParam String size,
      @RequestParam String name,
      @RequestParam String phone,
      @RequestParam(required = false) Boolean anonymous) {


        
    System.out.println(latitude);
    System.out.println(longitude);
    System.out.println(size);
    System.out.println(name);
    System.out.println(phone);
    System.out.println(anonymous);

    return "redirect:/";
  }
}
