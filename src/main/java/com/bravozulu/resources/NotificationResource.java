package com.bravozulu.resources;



import com.bravozulu.core.BidHistory;
import com.bravozulu.core.User;
import com.bravozulu.core.Transactions;
import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.NotificationDao;
import com.bravozulu.db.UserDAO;
import com.bravozulu.db.TransactionsDao;
import com.bravozulu.core.Notification;
import com.bravozulu.db.NotificationDao;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;


@Path("/notification")
@Api(value = "/notification", description = "Operatin on notification")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
    private final NotificationDao notificationDao;
    private final TransactionsDao transactionDao;
    private final UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(NotificationResource.class);


    public NotificationResource(NotificationDao notificationDao ,
                                TransactionsDao transactionDao , UserDAO userDAO) {
        this.notificationDao = notificationDao;
        this.transactionDao = transactionDao;
        this.userDAO = userDAO;
    }



    @GET
    @Path("/findAll")
    @RolesAllowed("ADMIN")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "search all notifications",
            response = Notification.class,
            responseContainer = "List")
    public List<Notification> findAllNotification() {
        return this.notificationDao.findAll();
    }

    @GET
    @Path("/notifyId={notifyId}/notification")
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public Notification findByNotificationId(@Auth User user, @PathParam("notifyId") LongParam
            notifyId) {
        return this.notificationDao.findByNotifyId(notifyId.get()).orElseThrow(() -> new
                NotFoundException("No such notification."));
    }


    @GET
    @Path("/transactionId={transactionId}/notification")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "search notifications by transactionId",
            notes = "Pass the transactionId as path parameter",
            response = Notification.class,
            responseContainer = "List")
    public List<Notification> findNotificationByTransactionID(
            @PathParam("transactionId") @ApiParam(value = "id that needs to searched", required = true) Long transactionId) {
        return this.notificationDao.findByTransactionId(transactionId);
    }

    @GET
    @Path("/findNotificationByUserID/{userId}")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "search notifications by user id",
            notes = "Pass the user id as path parameter",
            response = Notification.class,
            responseContainer = "List")
    public List<Notification> findNotificationByUserID(
            @PathParam("userId") @ApiParam(value = "id that needs to searched", required = true) Long userId) {
        return this.notificationDao.findByUserId(userId);
    }








}
