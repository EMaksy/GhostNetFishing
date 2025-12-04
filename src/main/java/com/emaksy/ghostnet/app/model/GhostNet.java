package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class GhostNet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String latitude;
  @NotBlank private String longitude;

  @NotNull
  @Enumerated(EnumType.STRING)
  private GhostNetSize size;

  @Enumerated(EnumType.STRING)
  private GhostNetStatus status;

  @ManyToOne
  @JoinColumn(name = "reporter_id")
  private Person reporter;

  @ManyToOne
  @JoinColumn(name = "rescuer_id")
  private Person rescuer;

  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
  }

  public GhostNet() {}

  public GhostNet(
      String latitude, String longitude, GhostNetSize size, GhostNetStatus status, Person person) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.size = size;
    this.status = status;
    this.reporter = person;
  }

  // Getter & Setter
  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setSize(GhostNetSize size) {
    this.size = size;
  }

  public GhostNetSize getSize() {
    return size;
  }

  public void setStatus(GhostNetStatus status) {
    this.status = status;
  }

  public GhostNetStatus getStatus() {
    return status;
  }

  public void setReporter(Person reporter) {
    this.reporter = reporter;
  }

  public Person getReporter() {
    return reporter;
  }

  public void setRescuer(Person rescuer) {
    this.rescuer = rescuer;
  }

  public Person getRescuer() {
    return rescuer;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getId() {
    return id;
  }
}
