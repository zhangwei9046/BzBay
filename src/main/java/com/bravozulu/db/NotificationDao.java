package com.bravozulu.db;

import com.bravozulu.core.Notification;
import com.bravozulu.core.Transaction;
import com.bravozulu.core.User;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import io.dropwizard.hibernate.AbstractDAO;

public class NotificationDao extends AbstractDAO<Notification> {

    private static Logger logger = LoggerFactory.getLogger(NotificationDao.class);

    public NotificationDao(SessionFactory factory) {
        super(factory);
    }

    public Notification create(Notification notification) {
        NotificationDao.logger.info(notification.toString());
        return persist(notification);
    }

    public Optional<Notification> findByNotifyId(Long notifyId) {
        return Optional.ofNullable(get(notifyId));
    }

    public List<Notification> findAll() {
        return list(namedQuery("com.bravozulu.core.Notification.findAll"));
    }

    public List<Notification> findByUserId(Long userId) {
        return list(
                namedQuery("com.bravozulu.core.Notification.findByuserId").setParameter("userId", userId));
    }

    public List<Notification> findByTransactionId(Long transactionId) {
        return list(namedQuery("com.bravozulu.core.Notification.findBytransactionId")
                .setParameter("transactionId", transactionId));
    }

}