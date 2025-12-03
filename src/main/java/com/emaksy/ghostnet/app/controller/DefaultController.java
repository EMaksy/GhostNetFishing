package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.controller.dto.ReportForm;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

  private final GhostNetRepository ghostNetRepository;

  public DefaultController(GhostNetRepository ghostNetRepository) {
    this.ghostNetRepository = ghostNetRepository;
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("formAction", "/");
    model.addAttribute("openRecoveries", ghostNetRepository.findAll());

    return "index";
  }
}
