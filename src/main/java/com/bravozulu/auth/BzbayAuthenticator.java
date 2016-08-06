package com.bravozulu.auth;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import javax.ws.rs.NotFoundException;


/**
 * Created by ying on 7/5/16.
 */
public class BzbayAuthenticator implements Authenticator<BasicCredentials, User>{
    private final UserDAO userDAO;
    private SessionFactory sessionFactory;

    public BzbayAuthenticator(UserDAO userDAO, SessionFactory sessionFactory) {
        this.userDAO = userDAO;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Valid users with mapping user -> roles
     */
//    private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
//            "guest", ImmutableSet.of(),
//            "good-guy", ImmutableSet.of("BASIC_GUY"),
//            "chief-wizard", ImmutableSet.of("ADMIN", "BASIC_GUY")
//    );

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) {
        Session session = this.sessionFactory.openSession();
        ManagedSessionContext.bind(session);
        User user = userDAO.findByUsername(credentials.getUsername()).orElseThrow(() -> new NotFoundException("Sign in fail"));
        if (user.getPassword().equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername(), user.isAdmin()));
        }
        return Optional.absent();
    }
}
