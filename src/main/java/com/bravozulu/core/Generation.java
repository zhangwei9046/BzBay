package com.bravozulu.core;


import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.NotificationDao;
import com.bravozulu.db.TransactionsDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.sql.Update;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

// update everyTable per min.
// 1. we check each item in item table, if current time equals startDate, update item available
// to be true, which means is available to bid;
// 2. if current time equals enddate:
// (1) update item status to be false, which means is not available to bid;
// (2) check BidHistory table to find the highest price bid, recording its userId
// (3) End the bid: add an new transaction in transaction Table
// (4) Send notification to bidder and seller.


public class Generation {
    NotificationDao notificationDao;
    TransactionsDao transactionDao;
    BidHistoryDAO bidHistoryDao;
    ItemDAO itemDao;
    SessionFactory sessionFactory;


    public Generation(NotificationDao notificationDao, TransactionsDao transactionDao,
                      BidHistoryDAO bidHistoryDao, ItemDAO itemDao, SessionFactory sessionFactory) {
        this.notificationDao = notificationDao;
        this.transactionDao = transactionDao;
        this.bidHistoryDao = bidHistoryDao;
        this.itemDao = itemDao;
        this.sessionFactory = sessionFactory;
    }



    public void generator() {
        Timer time = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Session session = Generation.this.sessionFactory.openSession();
                ManagedSessionContext.bind(session);
                long currentTime = new Date().getTime();
                List<Item> Allitem = Generation.this.itemDao.findAll();

                // check for each
                for (int i = 0; i < Allitem.size(); i++) {
                    Item thisItem = Allitem.get(i);
                    if ((currentTime >= thisItem.getStartDate().getTime())
                            && (currentTime < thisItem.getEndDate().getTime())) {
                        Generation.this.itemDao.updateAvailable(true, thisItem.getItemId());
                    } else {
                        Generation.this.itemDao.updateAvailable(false, thisItem.getItemId());
                    }


                    if (!Generation.this.transactionDao.findByItemId(thisItem.getItemId()).isPresent()) {
                        if ((currentTime >= thisItem.getEndDate().getTime())) {

                            Optional<BidHistory> WinBid =
                                    Generation.this.bidHistoryDao.findByHighestPriceByItemId(thisItem.getItemId());

                            if (WinBid.isPresent()) {
                                Transactions newTransaction = new Transactions(WinBid.get().getBidId(), thisItem.getItemId(),
                                        WinBid.get().getUserId(),WinBid.get().getPrice(), thisItem.getEndDate());
                                Generation.this.transactionDao.create(newTransaction);

                                String content = "Bid has end.";
                                Notification notification =
                                        new Notification(newTransaction.getTransactionId(), WinBid.get().getUserId(), content);

                                Generation.this.notificationDao.create(notification);
                            }
                        }
                    }
                }
                session.close();
            }
        };
        time.schedule(task, 0, 5000);
    }

}
