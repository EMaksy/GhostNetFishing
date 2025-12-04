package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "app_user")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;

  @NotBlank private String name;

  @NotBlank private String phone;

  @NotBlank
  @Column(name = "password_hash")
  private String passwordHash;

  @Column(nullable = false)
  private String role = "ROLE_USER";

  @OneToOne
  @JoinColumn(name = "person_id")
  private Person person;

  public AppUser() {}

  public AppUser(String username, String name, String phone, String passwordHash) {
    this.username = username;
    this.name = name;
    this.phone = phone;
    this.passwordHash = passwordHash;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
