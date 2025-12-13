package com.emaksy.ghostnet.app.service;

import com.emaksy.ghostnet.app.model.AppUser;
import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.repository.AppUserRepository;
import com.emaksy.ghostnet.app.repository.GhostNetRepository;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MyTasksService {

  private static final Set<GhostNetStatus> RESOLVED_STATUSES =
      Set.of(GhostNetStatus.RECOVERED, GhostNetStatus.MISSING);

  private final GhostNetRepository ghostNetRepository;
  private final AppUserRepository appUserRepository;

  public MyTasksService(
      GhostNetRepository ghostNetRepository, AppUserRepository appUserRepository) {
    this.ghostNetRepository = ghostNetRepository;
    this.appUserRepository = appUserRepository;
  }

  public List<GhostNet> activeTasks(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return List.of();
    }
    AppUser user = appUserRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null || user.getPerson() == null) {
      return List.of();
    }
    Person person = user.getPerson();
    return ghostNetRepository.findByRescuerAndStatusNotIn(person, RESOLVED_STATUSES);
  }

  public boolean isResolved(GhostNetStatus status) {
    return RESOLVED_STATUSES.contains(status);
  }
}
