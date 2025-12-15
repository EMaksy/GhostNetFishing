package com.emaksy.ghostnet.app.repository;

import com.emaksy.ghostnet.app.model.Person;
import com.emaksy.ghostnet.app.model.PersonRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  @Query("select distinct p from Person p join p.roles r where r = :role")
  List<Person> findByRole(@Param("role") PersonRole role);
}
