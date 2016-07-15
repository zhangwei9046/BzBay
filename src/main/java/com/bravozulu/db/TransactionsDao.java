package com.bravozulu.db;

import com.bravozulu.core.CreditCards;
import com.bravozulu.core.Transactions;
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

    public void delete(Long transactionId) {
        Transactions trans = findBytransactionId(transactionId).orElseThrow(() -> new NotFoundException("No such transactins."));
        currentSession().delete(trans);
    }


    public Transactions update(Transactions transaction,Long transactionId,) {
        transaction.settransactionId(transactionId);
        return persist(transaction);
    }
}
