package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.Subservice;

public class SubserviceTest {

    private SubscriptionDAO subscriptionDAO;
    Subservice subservice = new Subservice();
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        subscriptionDAO = mock(SubscriptionDAO.class);
    }

    @Test(expected = ServiceException.class)
    public void testIfbooksleft() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        String username = "abc";
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

        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        Boolean actual = subservice.ifbooksleft(username);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).getUserSub(
                username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, subservice.ifbooksleft(username));
    }

    @Test
    public void testIfbooksleft2() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        String username = "abc";
        UserSubs us = new UserSubs();

        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        Boolean actual = subservice.ifbooksleft(username);
        assertEquals(false, actual);
    }

    @Test(expected = ServiceException.class)
    public void testAddSub() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        String username = "abc";
        String subscription = "basic";
        int id = 1;
        int dur = 12;
        int max = 5;
        Subscriptions sub = new Subscriptions();

        when(subscriptionDAO.getSub()).thenReturn(Arrays.asList(sub));
        when(subscriptionDAO.invalidateSub(username)).thenReturn(true);
        when(subscriptionDAO.addSub(username, id, dur, max, subscription))
                .thenReturn(true);
        Boolean actual = subservice.addSub(username, subscription);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).getSub();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException,
                subservice.addSub(username, subscription));
    }

    @Test(expected = ServiceException.class)
    public void testUpdtSub() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        int subid = 1;
        String username = "abc";
        String subscription = "basic";
        int id = 1;
        int dur = 12;
        int max = 5;
        Subscriptions sub = new Subscriptions();

        when(subscriptionDAO.subDetails(subid)).thenReturn(Arrays.asList(sub));
        when(subscriptionDAO.invalidateSub(username)).thenReturn(true);
        when(subscriptionDAO.addSub(username, id, dur, max, subscription))
                .thenReturn(true);
        Boolean actual = subservice.updtSub(username, subid);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).subDetails(
                subid);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, subservice.updtSub(username, subid));
    }

    @Test(expected = ServiceException.class)
    public void testUserSub() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        String username = "abc";
        UserSubs us = new UserSubs();

        when(subscriptionDAO.userSub(username)).thenReturn(Arrays.asList(us));
        List<UserSubs> actual = subservice.userSub(username);
        assertEquals(Arrays.asList(us), actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).userSub(
                username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, subservice.userSub(username));
    }

    @Test(expected = ServiceException.class)
    public void testSubDetails() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        String username = "abc";
        int subid = 1;
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
        Subscriptions sub = new Subscriptions();

        when(subscriptionDAO.userSub(username)).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.subDetails(subid)).thenReturn(Arrays.asList(sub));
        List<Subscriptions> actual = subservice.subDetails(username);
        assertEquals(Arrays.asList(sub), actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).userSub(
                username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, subservice.subDetails(username));
    }

    @Test(expected = ServiceException.class)
    public void testGetSub() throws DAOException, ServiceException {

        subservice.setDAO(subscriptionDAO);
        Subscriptions sub = new Subscriptions();

        when(subscriptionDAO.getSub()).thenReturn(Arrays.asList(sub));
        List<Subscriptions> actual = subservice.getSub();
        assertEquals(Arrays.asList(sub), actual);

        doThrow(new DAOException("error")).when(subscriptionDAO).getSub();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, subservice.getSub());
    }

}
