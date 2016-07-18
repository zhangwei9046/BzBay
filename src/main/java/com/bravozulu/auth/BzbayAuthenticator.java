package com.bravozulu.auth;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.NotFoundException;


/**
 * Created by ying on 7/5/16.
 */
public class BzbayAuthenticator implements Authenticator<BasicCredentials, User>{
    private final UserDAO userDAO;

    public BzbayAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
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
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        User user = userDAO.findByUsername(credentials.getUsername()).orElseThrow(() -> new NotFoundException("Sign in fail"));
        if (user.getPassword().equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername(), user.isAdmin()));
        }
        return Optional.absent();
    }
}
