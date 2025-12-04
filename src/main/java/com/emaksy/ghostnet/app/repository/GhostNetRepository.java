package com.emaksy.ghostnet.app.repository;

import com.emaksy.ghostnet.app.model.GhostNet;
import com.emaksy.ghostnet.app.model.GhostNetStatus;
import com.emaksy.ghostnet.app.model.Person;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
  List<GhostNet> findByRescuer(Person rescuer);

  List<GhostNet> findByRescuerAndStatusNotIn(Person rescuer, Collection<GhostNetStatus> statuses);

  List<GhostNet> findByStatus(GhostNetStatus status);

  List<GhostNet> findByStatusIn(Collection<GhostNetStatus> statuses);
}
