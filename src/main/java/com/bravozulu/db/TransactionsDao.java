package com.bravozulu.db;


import com.bravozulu.core.Transactions;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;

import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Melody on 7/1/16.
 */
public class TransactionsDao extends AbstractDAO<Transactions>{
    public TransactionsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Transactions create(Transactions transaction) { return persist(transaction); }

    public Optional<Transactions> findBytransactionId(long id) {
        return Optional.ofNullable(get(id));
    }

    public List<Transactions> findAll() {
        return list(namedQuery("com.bravozulu.core.Transactions.findAll"));
    }

    public Optional<Transactions> findByBidHistoryId(Long bidhistoryId) {
        return Optional
                .ofNullable((Transactions) namedQuery("com.bravozulu.core.Transactions.findBybidhistoryId")
                        .setParameter("bidhistoryId", bidhistoryId).uniqueResult());
    }
    public Optional<Transactions> findByItemId(Long itemId) {
        return Optional.ofNullable((Transactions) namedQuery("com.bravozulu.core.Transactions.findByitemId")
                .setParameter("itemId", itemId).uniqueResult());
    }
    public Optional<Transactions> findByUserId(Long userId) {
        return Optional.ofNullable((Transactions) namedQuery("com.bravozulu.core.Transactions.findByuserId")
                .setParameter("userId", userId).uniqueResult());
    }

}
