package com.bravozulu.db;

import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by ying on 8/1/16.
 */
public class DAOTests {
    SessionFactory sessionFactory;

    public DAOTests() {
        Configuration config=new Configuration();
        config.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/test");
        config.setProperty("hibernate.connection.username","root");
        config.setProperty("hibernate.connection.password", "password");
        config.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
        config.setProperty("hibernate.current_session_context_class", "thread");
        config.setProperty("hibernate.show_sql", "false");
        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Review.class);

        sessionFactory=config.buildSessionFactory();
    }

    public Session getSession()
    {
        Session session;

        try {
            session = sessionFactory.getCurrentSession();
        } catch (SessionException se) {
            session = sessionFactory.openSession();
        }

        return session;
    }
}
