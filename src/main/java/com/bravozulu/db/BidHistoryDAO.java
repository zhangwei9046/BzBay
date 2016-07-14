package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Transactions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

/**
 * Created by ying on 6/25/16.
 */
public class BidHistoryDAO extends AbstractDAO<BidHistory>{
    public BidHistoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public BidHistory create(BidHistory bidhistory) { return persist(bidhistory); }

    public Optional<BidHistory> findBybidId(long id) {
        return Optional.ofNullable(get(id));
    }

    public void delete(Long bidId) {
        BidHistory bid = findBybidId(bidId).orElseThrow(() -> new NotFoundException("No such bid."));
        currentSession().delete(bid);
    }


    public BidHistory update(BidHistory bid,Long bidId,) {
        bid.setBidId(bidId);
        return persist(bidId);
    }
}
