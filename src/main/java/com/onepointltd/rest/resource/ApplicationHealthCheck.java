package com.onepointltd.rest.resource;

import com.codahale.metrics.health.HealthCheck;

public class ApplicationHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    boolean status = true;

    // Check application health and set the status accordingly

    if (!status) return Result.unhealthy("message");
    return Result.healthy();
  }
}
