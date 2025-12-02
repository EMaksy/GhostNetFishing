package com.emaksy.ghostnet.app.repository;

import com.emaksy.ghostnet.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
