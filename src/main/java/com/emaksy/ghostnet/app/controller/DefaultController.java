package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.service.MyTasksService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

  private final GhostNetRepository ghostNetRepository;
  private final MyTasksService myTasksService;

  public DefaultController(GhostNetRepository ghostNetRepository, MyTasksService myTasksService) {
    this.ghostNetRepository = ghostNetRepository;
    this.myTasksService = myTasksService;
  }

  @GetMapping("/")
  public String home(
      Model model,
      Authentication authentication,
      @RequestParam(name = "onlyReported", defaultValue = "true") boolean onlyReported) {
    model.addAttribute("formAction", "/");
    model.addAttribute(
        "openRecoveries",
        onlyReported
            ? ghostNetRepository.findByStatus(com.emaksy.ghostnet.app.model.GhostNetStatus.REPORTED)
            : ghostNetRepository.findAll());
    model.addAttribute("onlyReported", onlyReported);
    model.addAttribute("recoveriesBase", "/");
    model.addAttribute("myTasks", myTasksService.activeTasks(authentication));

    return "index";
  }
}
