package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

  private final GhostNetRepository ghostNetRepository;
  private final String mapsApiKey;

  public MapController(
      GhostNetRepository ghostNetRepository, @Value("${google.maps.api-key:}") String mapsApiKey) {
    this.ghostNetRepository = ghostNetRepository;
    this.mapsApiKey = mapsApiKey;
  }

  @GetMapping("/map")
  public String map(Model model) {
    model.addAttribute("openRecoveries", ghostNetRepository.findByStatus(GhostNetStatus.REPORTED));
    model.addAttribute("mapsApiKey", mapsApiKey);
    return "pages/map-page";
  }
}
