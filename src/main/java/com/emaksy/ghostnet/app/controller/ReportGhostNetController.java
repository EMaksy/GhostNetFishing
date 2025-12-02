package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  public String report() {
    return "pages/report-page";
  }

  @PostMapping("/report")
  public String submitReport(
      @RequestParam String latitude,
      @RequestParam String longitude,
      @RequestParam String size,
      @RequestParam String name,
      @RequestParam String phone,
      @RequestParam(required = false) Boolean anonymous) {

    System.out.println(latitude);
    System.out.println(longitude);
    System.out.println(size);
    System.out.println(name);
    System.out.println(phone);
    System.out.println(anonymous);

    Person person = new Person(name, phone);
    if (Boolean.TRUE.equals(anonymous)) {
      person.setPhone(null);
    }
    personRepository.save(person);

    GhostNet ghostNet = new GhostNet();
    ghostNet.setLatitude(latitude);
    ghostNet.setLongitude(longitude);
    ghostNet.setSize(size);
    ghostNet.setAnonymous(Boolean.TRUE.equals(anonymous));
    ghostNet.setStatus(GhostNetStatus.REPORTED);
    ghostNet.setReporter(person);

    ghostNetRepository.save(ghostNet);

    return "redirect:/";
  }
}
