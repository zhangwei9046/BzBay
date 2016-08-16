package com.bravozulu;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.auth.BzbayAuthorizer;
import com.bravozulu.core.*;
import com.bravozulu.db.*;
import com.bravozulu.health.AppHealthCheck;
import com.bravozulu.health.DatabaseHealthCheck;
import com.bravozulu.resources.*;
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
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


public class bzbayApplication extends Application<bzbayConfiguration> {

    private final HibernateBundle<bzbayConfiguration> hibernate = new HibernateBundle<bzbayConfiguration>(User.class, Review.class, Item.class, BidHistory.class, Transactions.class, Notification.class) {
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
        bootstrap.addBundle(new TemplateConfigBundle());

        //Swagger
        bootstrap.addBundle(new SwaggerBundle<bzbayConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(bzbayConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final bzbayConfiguration configuration,
                    final Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        environment.jersey().register(new UserResource(userDAO));

        final ReviewDAO reviewDAO = new ReviewDAO(hibernate.getSessionFactory());
        environment.jersey().register(new ReviewResource(reviewDAO, userDAO));

        final ItemDAO itemDAO = new ItemDAO(hibernate.getSessionFactory());
        environment.jersey().register(new ItemResource(itemDAO, userDAO));

        final BidHistoryDAO bidHistoryDAO = new BidHistoryDAO(hibernate.getSessionFactory());
        environment.jersey().register(new BidHistoryResource(bidHistoryDAO, itemDAO, userDAO));

        final TransactionsDao transactionDAO = new TransactionsDao(hibernate.getSessionFactory());
        environment.jersey().register(new TransactionResource(transactionDAO, bidHistoryDAO, itemDAO, userDAO));

        final NotificationDao notificationDAO = new NotificationDao(hibernate.getSessionFactory());
        environment.jersey().register(new NotificationResource(notificationDAO, transactionDAO, userDAO));

        Generation generation = new Generation(notificationDAO, transactionDAO, bidHistoryDAO, itemDAO,
                this.hibernate.getSessionFactory());
        generation.generator();

        // Adding health check
        AppHealthCheck appHealthCheck = new AppHealthCheck("it's alive");
        environment.healthChecks().register("it's alive", appHealthCheck);

        DataSourceFactory database  = configuration.getDataSourceFactory();
        environment.healthChecks().register("database", new
                DatabaseHealthCheck(database));

        //Authentication
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new BzbayAuthenticator(userDAO, this.hibernate.getSessionFactory()))
                        .setAuthorizer(new BzbayAuthorizer())
//                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}
