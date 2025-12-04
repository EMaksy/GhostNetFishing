package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.GhostNetStatus;
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
      @RequestParam(name = "status", required = false) String status) {
    GhostNetStatus selected = parseStatus(status);
    if (selected != null) {
      model.addAttribute("openRecoveries", ghostNetRepository.findByStatus(selected));
    } else {
      model.addAttribute("openRecoveries", ghostNetRepository.findAll());
    }
    model.addAttribute("selectedStatus", selected != null ? selected.name() : "ALL");
    model.addAttribute("recoveriesBase", "/recoveries");
    return "pages/recovery-page";
  }

  private GhostNetStatus parseStatus(String status) {
    if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
      return null;
    }
    try {
      return GhostNetStatus.valueOf(status);
    } catch (IllegalArgumentException ex) {
      return null;
    }
  }
}
