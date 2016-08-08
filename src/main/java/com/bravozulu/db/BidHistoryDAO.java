package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;


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

    public List<BidHistory> findByUserId(Long userId) {
        return list(namedQuery("com.bravozulu.core.BidHistory.findByuserId").setParameter("userId", userId));
    }
    public List<BidHistory> findByItemId(Long itemId) {
        return list(namedQuery("com.bravozulu.core.BidHistory.findByitemId").setParameter("itemId", itemId));
    }

    public Optional<BidHistory> findByHighestPriceByItemId(Long itemId) {
        List<BidHistory> list = list(
                namedQuery("com.bravozulu.core.BidHistory.findByHigherPrice").setParameter("itemId", itemId));
        if (!list.isEmpty()) {
            return Optional.ofNullable(list.get(0));
        }
        return Optional.empty();
    }



}
