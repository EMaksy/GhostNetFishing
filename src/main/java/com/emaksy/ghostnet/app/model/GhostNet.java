package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GhostNet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String latitude;
  private String longitude;
  private String size;

  @Enumerated(EnumType.STRING)
  private GhostNetStatus status;

  private boolean anonymous;

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

  // --- setters ---

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setStatus(GhostNetStatus status) {
    this.status = status;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public void setReporter(Person reporter) {
    this.reporter = reporter;
  }

  public void setRescuer(Person rescuer) {
    this.rescuer = rescuer;
  }
}
