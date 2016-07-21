package com.bravozulu.db;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Mark on 7/20/16.
 *
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    /**
     * Create the SessionFactory from standard (hibernate.cfg.xml) config file.
     */
    private static void configureSessionFactory() throws HibernateException{
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory
                    (serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns sessionFactory
     * @return SessionFactory object
     */
    public static SessionFactory getSessionFactory() {
        configureSessionFactory();
        return sessionFactory;
    }
}
