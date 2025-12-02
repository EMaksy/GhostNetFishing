package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String phone;

  public Person() {}

  public Person(String name, String phone) {
    this.name = name;
    this.phone = phone;
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
}
