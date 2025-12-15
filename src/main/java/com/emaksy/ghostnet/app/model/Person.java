package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String name;
  private boolean anonymous;
  private String phone;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "person_roles", joinColumns = @JoinColumn(name = "person_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Set<PersonRole> roles = new HashSet<>();

  public Person() {}

  public Person(String name, boolean anonymous) {
    this.name = name;
    this.anonymous = anonymous;
  }

  // Getter & Setter
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public Set<PersonRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<PersonRole> roles) {
    this.roles = roles != null ? roles : new HashSet<>();
  }

  public void addRole(PersonRole role) {
    if (role != null) {
      roles.add(role);
    }
  }

  public boolean isReporter() {
    return roles.contains(PersonRole.REPORTER);
  }

  public boolean isRescuer() {
    return roles.contains(PersonRole.RESCUER);
  }
}
