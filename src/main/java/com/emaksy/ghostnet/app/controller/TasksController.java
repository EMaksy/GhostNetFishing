package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.service.MyTasksService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasksController {

  private final MyTasksService myTasksService;

  public TasksController(MyTasksService myTasksService) {
    this.myTasksService = myTasksService;
  }

  @GetMapping("/tasks")
  public String tasks(Model model, Authentication authentication) {
    model.addAttribute("myTasks", myTasksService.activeTasks(authentication));
    return "pages/tasks-page";
  }
}
