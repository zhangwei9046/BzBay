package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by ying on 6/25/16.
 */
public class BidHistoryDAO extends AbstractDAO<BidHistory>{
    public BidHistoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
