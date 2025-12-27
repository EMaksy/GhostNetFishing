package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.service.MyTasksService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  private final GhostNetRepository ghostNetRepository;
  private final MyTasksService myTasksService;
  private final String mapsApiKey;

  public HomeController(
      GhostNetRepository ghostNetRepository,
      MyTasksService myTasksService,
      @Value("${google.maps.api-key:}") String mapsApiKey) {
    this.ghostNetRepository = ghostNetRepository;
    this.myTasksService = myTasksService;
    this.mapsApiKey = mapsApiKey;
  }

  @GetMapping("/")
  public String home(
      Model model,
      Authentication authentication,
      @RequestParam(name = "status", required = false) String status) {
    model.addAttribute("formAction", "/");
    GhostNetStatus selected = parseStatus(status);
    if (selected != null) {
      model.addAttribute("openRecoveries", ghostNetRepository.findByStatus(selected));
    } else {
      model.addAttribute("openRecoveries", ghostNetRepository.findAll());
    }
    model.addAttribute("selectedStatus", selected != null ? selected.name() : "ALL");
    model.addAttribute("recoveriesBase", "/");
    model.addAttribute("myTasks", myTasksService.activeTasks(authentication));
    model.addAttribute("mapsApiKey", mapsApiKey);

    return "index";
  }

  @GetMapping("/home")
  public String redirectHome() {
    return "redirect:/";
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
