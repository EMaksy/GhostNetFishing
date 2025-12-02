package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.PersonRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  private final PersonRepository personRepository;

  public ApiController(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @GetMapping("/api")
  public String getData() {
    return "Sample Data from API";
  }

  @GetMapping("/api/persons")
  public List<Person> listPersons() {
    return personRepository.findAll();
  }
}
