package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

  private final GhostNetRepository ghostNetRepository;

  public MapController(GhostNetRepository ghostNetRepository) {
    this.ghostNetRepository = ghostNetRepository;
  }

  @GetMapping("/map")
  public String map(Model model) {
    model.addAttribute("openRecoveries", ghostNetRepository.findByStatus(GhostNetStatus.REPORTED));
    return "pages/map-page";
  }
}
