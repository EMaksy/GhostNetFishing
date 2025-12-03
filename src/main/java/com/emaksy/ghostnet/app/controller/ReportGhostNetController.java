package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetSize;
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
      @RequestParam GhostNetSize size,
      @RequestParam String name,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) Boolean anonymous) {

    System.out.println(latitude);
    System.out.println(longitude);
    System.out.println(size);
    System.out.println(name);
    System.out.println(phone);
    System.out.println(anonymous);

    boolean isAnonymous = Boolean.TRUE.equals(anonymous);

    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }

    if (!isAnonymous && (phone == null || phone.isBlank())) {
      throw new IllegalArgumentException("Phone number is required for non-anonymous reports");
    }

    Person person = new Person(name, isAnonymous);
    if (!isAnonymous) {
      person.setPhone(phone);
    }
    personRepository.save(person);

    GhostNetStatus ghostNetStatus = GhostNetStatus.REPORTED;
    GhostNet ghostNet = new GhostNet(latitude, longitude, size, ghostNetStatus, person);
    ghostNetRepository.save(ghostNet);

    return "redirect:/";
  }
}
