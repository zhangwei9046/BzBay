package com.bravozulu.views;

import com.bravozulu.resources.UserResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class bzbayApplication extends Application<bzbayConfiguration> {

    public static void main(final String[] args) throws Exception {
        new bzbayApplication().run(args);
    }

    @Override
    public String getName() {
        return "bzbay";
    }

    @Override
    public void initialize(final Bootstrap<bzbayConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<bzbayConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(bzbayConfiguration
                                                                  configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final bzbayConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new UserResource());
    }

}
