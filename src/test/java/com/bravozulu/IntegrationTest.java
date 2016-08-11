package com.bravozulu;

import io.dropwizard.testing.ResourceHelpers;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;

/**
 * Created by ying on 7/18/16.
 */
public class IntegrationTest {
//    @ClassRule
//    public static final DropwizardAppRule<bzbayConfiguration> RULE =
//            new DropwizardAppRule<bzbayConfiguration>(bzbayApplication.class, ResourceHelpers.resourceFilePath("my-app-config.yaml"));
//
//    @Test
//    public void loginHandlerRedirectsAfterPost() {
//        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
//
//        Response response = client.target(
//                String.format("http://localhost:%d/login", RULE.getLocalPort()))
//                .request()
//                .post(Entity.json(loginForm()));
//
//        assertThat(response.getStatus()).isEqualTo(302);
//    }
}
