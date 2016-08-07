package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.CreditCards;
import com.bravozulu.core.Transactions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ying on 6/25/16.
 */
public class BidHistoryDAO extends AbstractDAO<BidHistory>{
    public BidHistoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public BidHistory create(BidHistory bidhistory) { return persist(bidhistory); }
    public List<BidHistory> findAll() {
        return list(namedQuery("com.bravozulu.core.BidHistory.findAll"));
    }
    public Optional<BidHistory> findBybidId(long id) {
        return Optional.ofNullable(get(id));
    }

 //find by userid, find by item id, find by bitid and userid
    //notification


}
