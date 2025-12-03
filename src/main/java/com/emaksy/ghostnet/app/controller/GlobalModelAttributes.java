package com.emaksy.ghostnet.app.controller;

import com.emaksy.ghostnet.app.controller.dto.ReportForm;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

  @ModelAttribute("reportForm")
  public ReportForm reportForm() {
    return new ReportForm();
  }
}
