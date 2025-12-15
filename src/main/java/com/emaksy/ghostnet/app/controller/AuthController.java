package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.controller.dto.SignupForm;
import com.emaksy.ghostnet.app.model.AppUser;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.model.PersonRole;
import com.emaksy.ghostnet.app.repository.AppUserRepository;
import com.emaksy.ghostnet.app.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  private final AppUserRepository appUserRepository;
  private final PersonRepository personRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(
      AppUserRepository appUserRepository,
      PersonRepository personRepository,
      PasswordEncoder passwordEncoder) {
    this.appUserRepository = appUserRepository;
    this.personRepository = personRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    if (!model.containsAttribute("signupForm")) {
      model.addAttribute("signupForm", new SignupForm());
    }
    return "pages/signup";
  }

  @PostMapping("/signup")
  public String handleSignup(
      @Valid @ModelAttribute("signupForm") SignupForm form, BindingResult bindingResult) {
    if (!form.getPassword().equals(form.getConfirmPassword())) {
      bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
    }

    appUserRepository
        .findByUsername(form.getUsername())
        .ifPresent(
            user ->
                bindingResult.rejectValue(
                    "username", "username.exists", "Username is already taken"));

    if (bindingResult.hasErrors()) {
      return "pages/signup";
    }

    Person person = new Person(form.getName(), false);
    person.setPhone(form.getPhone());
    person.addRole(PersonRole.RESCUER);
    personRepository.save(person);

    AppUser user =
        new AppUser(
            form.getUsername(),
            form.getName(),
            form.getPhone(),
            passwordEncoder.encode(form.getPassword()));
    user.setPerson(person);
    appUserRepository.save(user);

    return "redirect:/login?signupSuccess";
  }

  @GetMapping("/login")
  public String login() {
    return "pages/login";
  }
}
