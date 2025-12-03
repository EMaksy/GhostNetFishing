package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecoveriesController {

  private final GhostNetRepository ghostNetRepository;

  public RecoveriesController(GhostNetRepository ghostNetRepository) {
    this.ghostNetRepository = ghostNetRepository;
  }

  @GetMapping("/recoveries")
  public String recoveries(Model model) {
    model.addAttribute("openRecoveries", ghostNetRepository.findAll());
    return "pages/recovery-page";
  }
}
