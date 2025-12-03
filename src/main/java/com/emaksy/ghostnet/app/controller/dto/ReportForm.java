package com.emaksy.ghostnet.app.controller.dto;

import com.emaksy.ghostnet.app.model.GhostNetSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReportForm {

  @NotBlank private String latitude;

  @NotBlank private String longitude;

  @NotNull private GhostNetSize size;

  @NotBlank private String name;

  private String phone;
  private Boolean anonymous = false;

  // getters & setters …

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public GhostNetSize getSize() {
    return size;
  }

  public void setSize(GhostNetSize size) {
    this.size = size;
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

  public Boolean getAnonymous() {
    return anonymous;
  }

  public void setAnonymous(Boolean anonymous) {
    this.anonymous = anonymous;
  }

  public boolean isAnonymous() {
    return Boolean.TRUE.equals(anonymous);
  }
}
