package com.bravozulu.db;

import com.bravozulu.db.HibernateUtil;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.relational.Database;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by bonicma on 7/20/16.
 */
public class ItemDAOTest {
    private static final SessionFactory SESSION_FACTORY =
        HibernateUtil.getSessionFactory();
    private static Liquibase liquibase = null;
    private Session session;
    private Transaction tx;
    private ItemDAO dao;

/*
    @BeforeClass
    public static void setUpClass throws LiquibaseException, SQLException {
        SessionFactoryImpl sessionFactorImpl = (SessionFactoryImpl)
                SESSION_FACTORY;
        DriverManagerConnectionProviderImpl provider =
                (DriverManagerConnectionProviderImpl) sessionFactorImpl
                        .getConnectionProvider();
        Connection connection = provider.getConnection();
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection
                        (connection));

        liquibase = new Liquibase("migrations.xml", new
                ClassLoaderResourceAccessor(), database));
    }
*/
}
