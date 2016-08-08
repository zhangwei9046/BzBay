package com.bravozulu.core;


import com.bravozulu.db.BidHistoryDao;
import com.bravozulu.db.ItemDao;
import com.bravozulu.db.NotificationDao;
import com.bravozulu.db.TransactionsDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class Generation {
    NotificationDao notificationDao;
    TransactionsDao transactionDao;
    BidHistoryDao bidHistoryDao;
    ItemDao itemDao;
    SessionFactory sessionFactory;


    public Generation(NotificationDao notificationDao, TransactionsDao transactionsDao,
                  BidHistoryDao bidHistoryDao, ItemDao itemDao, SessionFactory sessionFactory) {
        this.notificationDao = notificationDao;
        this.transactionDao = transactionDao;
        this.bidHistoryDao = bidHistoryDao;
        this.itemDao = itemDao;
        this.sessionFactory = sessionFactory;
    }

    // update everyTable per second.
    // The logic is: Each second,
    // 1. we check each item in item table, if current time equals startDate, update item available
    // to be true, which means is available to bid;
    // 2. if current time equals enddate:
    // (1) update item status to be false, which means is not available to bid;
    // (2) check BidHistory table to find the highest price bid, recording its userId
    // (3) End the bid: add an new transaction in transaction Table
    // (4) Send notification to bidder and seller.


    public void generator() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Session session = Update.this.sessionFactory.openSession();
                ManagedSessionContext.bind(session);
                List<Item> Allitem = Update.this.itemDao.findAll();
                Date currentTime = new Date();

                for (int i = 0; i < Allitem.size(); i++) {
                    Item thisItem = Allitem.get(i);

                    if ((currentTime.getTime() >= thisItem.getStartDate().getTime())
                            && (currentTime.getTime() < thisItem.getEndDate().getTime())) {
                        Update.this.itemDao.updateStatus(true, thisItem.getItemId());
                    } else {
                        Update.this.itemDao.updateStatus(false, thisItem.getItemId());
                    }


                    if (!Update.this.transactionDao.findByItemId(thisItem.getItemId()).isPresent()) {
                        if ((currentTime.getTime() >= thisItem.getEndDate().getTime())) {

                            Optional<BidHistory> WinBid =
                                    Update.this.bidHistoryDao.findByHighestPriceByItemId(thisItem.getItemId());

                            if (WinBid.isPresent()) {
                                Transactions newTransaction = new Transactions(WinBid.get().getBidId(), thisItem.getItemId(),
                                        WinBid.get().getUserId(),WinBid.get().getPrice(), thisItem.getEndDate());
                                Update.this.transactionDao.create(newTransaction);

                                String content = "Bid has end.";
                                Notification notification_1 =
                                        new Notification(newTransaction.getTransactionId(), WinBid.get().getUserId(), content);
                                Notification notification_2 =
                                        new Notification(newTransaction.getTransactionId(), WinBid.get().getUserId(), content;
                                Update.this.notificationDao.create(notification_1);
                                Update.this.notificationDao.create(notification_2);
                            }
                        }
                    }
                }
                session.close();
            }
        };
        t.schedule(task, 0, 1000);
    }

}
