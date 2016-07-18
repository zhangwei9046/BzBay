package com.bravozulu.auth;

import com.bravozulu.core.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by ying on 7/5/16.
 */
public class BzbayAuthorizer implements Authorizer<User> {

    public BzbayAuthorizer() {}

    public boolean authorize(User user, String s) {
        return user.isAdmin();
    }
}
