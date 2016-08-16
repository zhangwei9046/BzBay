package com.bravozulu.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by Mark on 7/20/16.
 */

public class AppHealthCheck extends HealthCheck{
    private final String alive;
    public AppHealthCheck(String alive) {
        this.alive = alive;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
