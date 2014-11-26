package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.MailService;
import com.impetus.bookstore.service.NotifyService;

public class NotifyServiceTest {

    private SubscriptionDAO subscriptionDAO;
    private MailService mailService;
    NotifyService notifyService = new NotifyService();

    @Before
    public void setUp() {
        subscriptionDAO = mock(SubscriptionDAO.class);
        mailService = mock(MailService.class);
    }

    @Test
    public void testSendNotification() throws DAOException {

        notifyService.setService(mailService);
        notifyService.setDAO(subscriptionDAO);
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);

        when(subscriptionDAO.getActvUser()).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(mailService.sendNotification(username, 30)).thenReturn(true);
        Boolean actual = notifyService.sendNotification();
        assertEquals(true, actual);
    }

    @Test
    public void testSendNotificationCatch() throws DAOException {

        notifyService.setService(mailService);
        notifyService.setDAO(subscriptionDAO);
        doThrow(new DAOException("error")).when(subscriptionDAO).getActvUser();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(false, notifyService.sendNotification());
    }

    @Test
    public void testSendNotification2() throws DAOException {

        notifyService.setService(mailService);
        notifyService.setDAO(subscriptionDAO);
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date endDate = cal.getTime();

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);

        when(subscriptionDAO.getActvUser()).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(mailService.sendNotification(username, 7)).thenReturn(true);
        Boolean actual = notifyService.sendNotification();
        assertEquals(true, actual);
    }

    @Test
    public void testSendNotification3() throws DAOException {

        notifyService.setService(mailService);
        notifyService.setDAO(subscriptionDAO);
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date endDate = cal.getTime();

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);

        when(subscriptionDAO.getActvUser()).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(mailService.sendNotification(username, 1)).thenReturn(true);
        Boolean actual = notifyService.sendNotification();
        assertEquals(true, actual);
    }

    @Test
    public void testSendNotification4() throws DAOException {

        notifyService.setService(mailService);
        notifyService.setDAO(subscriptionDAO);
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);
        Date endDate = cal.getTime();

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);

        when(subscriptionDAO.getActvUser()).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(mailService.sendNotification(username, 2)).thenReturn(true);
        Boolean actual = notifyService.sendNotification();
        assertEquals(true, actual);
    }
}
