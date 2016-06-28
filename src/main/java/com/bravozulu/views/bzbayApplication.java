package com.bravozulu.views;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.bravozulu.resources.UserResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class bzbayApplication extends Application<bzbayConfiguration> {

    private final HibernateBundle<bzbayConfiguration> hibernate = new HibernateBundle<bzbayConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(bzbayConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

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
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final bzbayConfiguration configuration,
                    final Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        environment.jersey().register(new UserResource(userDAO));
    }

}
