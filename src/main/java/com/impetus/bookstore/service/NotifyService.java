package com.impetus.bookstore.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class NotifyService.
 */
@Service
public class NotifyService {

    /** The subscription dao. */
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /**
     * Sets the dao.
     * 
     * @param subscriptionDAO
     *            the new dao
     */
    public void setDAO(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    /**
     * Sets the service.
     * 
     * @param mailService
     *            the new service
     */
    public void setService(MailService mailService) {
        this.mailService = mailService;
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(NotifyService.class);

    /**
     * Send notification.
     * 
     * @return true, if successful
     */
    @Transactional
    public boolean sendNotification() {
        try {
            LOG.debug("Checking users to be notified");

            List<UserSubs> temp1 = new ArrayList<UserSubs>();
            List<UserSubs> temp2 = new ArrayList<UserSubs>();
            Calendar cal = Calendar.getInstance();
            Date endDate = new Date();
            Date aMonth = new Date();
            Date aWeek = new Date();
            Date aDay = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            temp1 = subscriptionDAO.getActvUser();
            for (UserSubs user : temp1) {

                String username = user.getUsername();
                temp2 = subscriptionDAO.getUserSub(username);
                endDate = temp2.get(0).getEndDate();

                cal.add(Calendar.DATE, 30);
                aMonth = cal.getTime();

                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 7);
                aWeek = cal.getTime();

                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                aDay = cal.getTime();

                if (ft.format(endDate).compareTo(ft.format(aMonth)) == 0) {
                    LOG.debug("Sending mail to " + username
                            + " subscription expiring on " + ft.format(aMonth));
                    mailService.sendNotification(username, 30);
                } else if (ft.format(endDate).compareTo(ft.format(aWeek)) == 0) {
                    LOG.debug("Sending mail to " + username
                            + " subscription expiring on " + ft.format(aWeek));
                    mailService.sendNotification(username, 7);
                } else if (ft.format(endDate).compareTo(ft.format(aDay)) == 0) {
                    LOG.debug("Sending mail to " + username
                            + " subscription expiring on " + ft.format(aDay));
                    mailService.sendNotification(username, 1);
                } else {
                    LOG.debug(username + " doesnt needs to be notified");
                }
            }
            return true;
        } catch (DAOException e) {
            LOG.error("Could not notify user");
            return false;
        }
    }

}
