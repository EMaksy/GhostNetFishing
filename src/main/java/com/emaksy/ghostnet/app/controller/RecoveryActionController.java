package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.model.AppUser;
import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.AppUserRepository;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import com.emaksy.ghostnet.app.repository.PersonRepository;
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
      @PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
    Optional<GhostNet> netOptional = ghostNetRepository.findById(id);
    if (netOptional.isEmpty()) {
      redirectAttributes.addFlashAttribute("errorMessage", "Ghost net not found.");
      return "redirect:/recoveries";
    }

    GhostNet net = netOptional.get();
    if (net.getStatus() != GhostNetStatus.REPORTED || net.getRescuer() != null) {
      redirectAttributes.addFlashAttribute("errorMessage", "Recovery cannot be taken over.");
      return "redirect:/recoveries";
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
    return "redirect:/recoveries";
  }
}
