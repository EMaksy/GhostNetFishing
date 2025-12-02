package com.emaksy.ghostnet.app.repository;

import com.emaksy.ghostnet.app.model.GhostNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {}
