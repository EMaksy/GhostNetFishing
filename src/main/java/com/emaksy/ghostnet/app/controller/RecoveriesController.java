package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecoveriesController {

  private final GhostNetRepository ghostNetRepository;

  public RecoveriesController(GhostNetRepository ghostNetRepository) {
    this.ghostNetRepository = ghostNetRepository;
  }

  @GetMapping("/recoveries")
  public String recoveries(
      Model model,
      @RequestParam(name = "onlyReported", defaultValue = "false") boolean onlyReported) {
    if (onlyReported) {
      model.addAttribute(
          "openRecoveries",
          ghostNetRepository.findByStatus(com.emaksy.ghostnet.app.model.GhostNetStatus.REPORTED));
    } else {
      model.addAttribute("openRecoveries", ghostNetRepository.findAll());
    }
    model.addAttribute("onlyReported", onlyReported);
    model.addAttribute("recoveriesBase", "/recoveries");
    return "pages/recovery-page";
  }
}
