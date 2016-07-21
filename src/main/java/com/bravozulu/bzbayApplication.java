package com.bravozulu;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.auth.BzbayAuthorizer;
import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.bravozulu.resources.UserResource;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


public class BzbayApplication extends Application<BzbayConfiguration> {

    private final HibernateBundle<BzbayConfiguration> hibernate = new HibernateBundle<BzbayConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(BzbayConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new BzbayApplication().run(args);
    }

    @Override
    public String getName() {
        return "bzbay";
    }

    @Override
    public void initialize(final Bootstrap<BzbayConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<BzbayConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(BzbayConfiguration
                                                                  configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new TemplateConfigBundle());
    }

    @Override
    public void run(final BzbayConfiguration configuration,
                    final Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        environment.jersey().register(new UserResource(userDAO));

        // Adding health check
        //final TemplateHealthCheck healthCheck =
        //        new TemplateHealthCheck(configuration.getTemplate());

        //environment.healthChecks().register("template", healthCheck);


        //Authentication
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new BzbayAuthenticator(userDAO))
                        .setAuthorizer(new BzbayAuthorizer())
//                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}
