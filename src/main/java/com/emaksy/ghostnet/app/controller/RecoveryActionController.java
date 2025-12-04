package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.AppUser;
import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.AppUserRepository;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecoveryActionController {

  private final GhostNetRepository ghostNetRepository;
  private final AppUserRepository appUserRepository;
  private final PersonRepository personRepository;

  public RecoveryActionController(
      GhostNetRepository ghostNetRepository,
      AppUserRepository appUserRepository,
      PersonRepository personRepository) {
    this.ghostNetRepository = ghostNetRepository;
    this.appUserRepository = appUserRepository;
    this.personRepository = personRepository;
  }

  @PostMapping("/recoveries/{id}/join")
  public String joinRecovery(
      @PathVariable Long id,
      Authentication authentication,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    Optional<GhostNet> netOptional = ghostNetRepository.findById(id);
    if (netOptional.isEmpty()) {
      redirectAttributes.addFlashAttribute("errorMessage", "Ghost net not found.");
      return redirectToPrevious(request);
    }

    GhostNet net = netOptional.get();
    if (net.getStatus() != GhostNetStatus.REPORTED || net.getRescuer() != null) {
      redirectAttributes.addFlashAttribute("errorMessage", "Recovery cannot be taken over.");
      return redirectToPrevious(request);
    }

    AppUser user =
        appUserRepository
            .findByUsername(authentication.getName())
            .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

    Person rescuer = user.getPerson();
    if (rescuer == null) {
      rescuer = new Person(user.getName(), false);
      rescuer.setPhone(user.getPhone());
      personRepository.save(rescuer);
      user.setPerson(rescuer);
      appUserRepository.save(user);
    }

    net.setRescuer(rescuer);
    net.setStatus(GhostNetStatus.RESCUE_IMMINENT);
    ghostNetRepository.save(net);

    redirectAttributes.addFlashAttribute("successMessage", "You joined this recovery.");
    return redirectToPrevious(request);
  }

  @PostMapping("/recoveries/{id}/mark-recovered")
  public String markRecovered(
      @PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
    Optional<GhostNet> netOptional = ghostNetRepository.findById(id);
    if (netOptional.isEmpty()) {
      redirectAttributes.addFlashAttribute("errorMessage", "Ghost net not found.");
      return "redirect:/";
    }
    GhostNet net = netOptional.get();
    AppUser user =
        appUserRepository
            .findByUsername(authentication.getName())
            .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    Person person = ensurePerson(user);

    if (net.getRescuer() == null || !net.getRescuer().getId().equals(person.getId())) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "You are not assigned to this recovery.");
      return "redirect:/";
    }

    if (net.getStatus() != GhostNetStatus.RESCUE_IMMINENT) {
      redirectAttributes.addFlashAttribute("errorMessage", "Recovery is not in progress.");
      return "redirect:/";
    }

    net.setStatus(GhostNetStatus.SAVED);
    ghostNetRepository.save(net);
    redirectAttributes.addFlashAttribute("successMessage", "Marked as recovered.");
    return "redirect:/";
  }

  @PostMapping("/recoveries/{id}/mark-missed")
  public String markMissed(
      @PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
    Optional<GhostNet> netOptional = ghostNetRepository.findById(id);
    if (netOptional.isEmpty()) {
      redirectAttributes.addFlashAttribute("errorMessage", "Ghost net not found.");
      return "redirect:/";
    }
    GhostNet net = netOptional.get();

    AppUser user =
        appUserRepository
            .findByUsername(authentication.getName())
            .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    ensurePerson(user); // ensures non-anonymous identity

    if (net.getStatus() == GhostNetStatus.SAVED) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "Recovered nets cannot be marked missing.");
      return "redirect:/";
    }

    net.setStatus(GhostNetStatus.MISSED);
    ghostNetRepository.save(net);
    redirectAttributes.addFlashAttribute("successMessage", "Marked as missing.");
    return "redirect:/";
  }

  private String redirectToPrevious(HttpServletRequest request) {
    String referer = request.getHeader("Referer");
    if (referer != null && referer.contains("/recoveries")) {
      return "redirect:/recoveries";
    }
    return "redirect:/";
  }

  private Person ensurePerson(AppUser user) {
    Person person = user.getPerson();
    if (person == null) {
      person = new Person(user.getName(), false);
      person.setPhone(user.getPhone());
      personRepository.save(person);
      user.setPerson(person);
      appUserRepository.save(user);
    }
    return person;
  }
}
