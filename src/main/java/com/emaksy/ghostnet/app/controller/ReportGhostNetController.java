package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.controller.dto.ReportForm;
import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportGhostNetController {

  private final PersonRepository personRepository;
  private final GhostNetRepository ghostNetRepository;

  public ReportGhostNetController(
      PersonRepository personRepository, GhostNetRepository ghostNetRepository) {
    this.personRepository = personRepository;
    this.ghostNetRepository = ghostNetRepository;
  }

  @GetMapping("/report")
  public String report(Model model) {
    if (!model.containsAttribute("reportForm")) {
      model.addAttribute("reportForm", new ReportForm());
    }
    return "pages/report-page";
  }

  @PostMapping("/report")
  public String submitReport(
      @Valid @ModelAttribute("reportForm") ReportForm form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      return "pages/report-page";
    }

    boolean isAnonymous = form.isAnonymous();

    Person person = new Person(form.getName(), isAnonymous);
    if (!isAnonymous) {
      person.setPhone(form.getPhone());
    }
    personRepository.save(person);

    GhostNet ghostNet =
        new GhostNet(
            form.getLatitude(),
            form.getLongitude(),
            form.getSize(),
            GhostNetStatus.REPORTED,
            person);
    ghostNetRepository.save(ghostNet);

    return "redirect:/";
  }
}
