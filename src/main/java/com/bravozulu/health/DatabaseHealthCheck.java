package com.bravozulu.health;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.db.DataSourceFactory;
import liquibase.database.Database;

/**
 * Created by bonicma on 8/16/16.
 */
public class DatabaseHealthCheck extends HealthCheck {
    private final DataSourceFactory database;

    public DatabaseHealthCheck(DataSourceFactory database) {
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        if (database.getCheckConnectionOnConnect()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Database is not connected.");
        }
    }
}
