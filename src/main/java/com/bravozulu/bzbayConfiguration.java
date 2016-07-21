package com.bravozulu;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DatabaseConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class BzbayConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    private static final Logger LOGGER = LoggerFactory.getLogger(BzbayConfiguration.class);

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory){
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        LOGGER.info("Dropwizard dummy DB URL (will be overridden)=" + database.getUrl());
        if(System.getenv("DATABASE_URL") != null) {
            DatabaseConfiguration databaseConfiguration = BzbayDatabaseConfiguration.create(System.getenv("DATABASE_URL"));
            database = (DataSourceFactory) databaseConfiguration.getDataSourceFactory(null);
            LOGGER.info("Heroku DB URL=" + database.getUrl());
        }
        return database;
    }


}
