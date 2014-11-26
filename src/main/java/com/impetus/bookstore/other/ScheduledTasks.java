package com.impetus.bookstore.other;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.service.RecommService;
import com.impetus.bookstore.service.NotifyService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScheduledTasks.
 */
public class ScheduledTasks {

    /** The recomm service. */
    @Autowired
    private RecommService recommService;

    /** The notify service. */
    @Autowired
    private NotifyService notifyService;

    /**
     * Recommendations2.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void recommendations2() {
        try {
            recommService.newPopular();
        } catch (ServiceException e) {
            LOG.error("Scheduler interrupted - " + e.getExceptionMsg()
                    + " Cause : " + e.getExceptionCause());
        }
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(RecommService.class);

    /**
     * Recommendations.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void recommendations() throws IOException {
        try {
            recommService.recommendations();
        } catch (ServiceException e) {
            LOG.error("Scheduler interrupted - " + e.getExceptionMsg()
                    + " Cause : " + e.getExceptionCause());
        }
    }

    /**
     * Notify user.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void notifyUser() {
        notifyService.sendNotification();
    }

    // */5 * * * * ?
    // 0 0 9 * * ?

}
