package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Transactions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

/**
 * Created by Melody on 7/1/16.
 */
public class TransactionsDao extends AbstractDAO<Transactions>{
    public TransactionsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Optional<Transactions> findBytransactionId(long id) {
        return Optional.ofNullable(get(id));
    }
    public void deleteTransaction(long id) {}
}
