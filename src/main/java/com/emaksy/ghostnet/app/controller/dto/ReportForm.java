package com.emaksy.ghostnet.app.controller.dto;

import com.emaksy.ghostnet.app.model.GhostNetSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ReportForm {

  @NotBlank
  @Pattern(
      regexp = "^-?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$",
      message = "Latitude must be between -90 and 90")
  private String latitude;

  @NotBlank
  @Pattern(
      regexp = "^-?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$",
      message = "Longitude must be between -180 and 180")
  private String longitude;

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
