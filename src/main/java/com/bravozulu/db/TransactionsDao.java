package com.bravozulu.db;


import com.bravozulu.core.Transaction;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by Melody on 7/1/16.
 */
public class TransactionsDao extends AbstractDAO<Transaction>{
    public TransactionsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Transaction create(Transaction transaction) { return persist(transaction); }

    public Optional<Transaction> findBytransactionId(long id) {
        return Optional.ofNullable(get(id));
    }

    public List<Transaction> findAll() {
        return list(namedQuery("com.bravozulu.core.Transaction.findAll"));
    }

    public Optional<Transaction> findByBidHistoryId(Long bidhistoryId) {
        return Optional
                .ofNullable((Transaction) namedQuery("com.bravozulu.core.Transaction.findBybidhistoryId")
                        .setParameter("bidhistoryId", bidhistoryId).uniqueResult());
    }
    public Optional<Transaction> findByItemId(Long itemId) {
        return Optional.ofNullable((Transaction) namedQuery("com.bravozulu.core.Transaction.findByitemId")
                .setParameter("itemId", itemId).uniqueResult());
    }
    public Optional<Transaction> findByUserId(Long userId) {
        return Optional.ofNullable((Transaction) namedQuery("com.bravozulu.core.Transaction.findByuserId")
                .setParameter("userId", userId).uniqueResult());
    }

}
