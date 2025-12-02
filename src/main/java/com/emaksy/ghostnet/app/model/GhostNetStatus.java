package com.emaksy.ghostnet.app.model;

import jakarta.persistence.*;

public enum GhostNetStatus {
  REPORTED,
  RESCUE_IMMINENT,
  SAVED,
  MISSED
}
