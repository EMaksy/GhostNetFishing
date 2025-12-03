package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.controller.dto.ReportForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

  @GetMapping("/")
  public String home(Model model) {
    if (!model.containsAttribute("reportForm")) {
      model.addAttribute("reportForm", new ReportForm());
    }
    return "index";
  }
}
