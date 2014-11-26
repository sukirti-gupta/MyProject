package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.UserService;

public class UserServiceTest {

    private SubscriptionDAO subscriptionDAO;
    private UserDAO userDAO;
    private Subscriptions subscriptions;
    UserService userService = new UserService();
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        subscriptionDAO = mock(SubscriptionDAO.class);
        userDAO = mock(UserDAO.class);
        subscriptions = mock(Subscriptions.class);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test(expected = ServiceException.class)
    public void testEmail() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        String eid = "xyz@gmail.com";
        List userdetails = new ArrayList();

        when(userDAO.getUserByEmail(eid)).thenReturn(userdetails);
        Boolean actual = userService.email(eid);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByEmail(eid);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.email(eid));
    }

    @Test(expected = ServiceException.class)
    public void testEmail2() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        UserDetails ud = new UserDetails();

        when(userDAO.getUserByEmail("xyz@gmail.com")).thenReturn(Arrays.asList(ud));
        Boolean actual = userService.email("xyz@gmail.com");
        assertEquals(false, actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByEmail(
                "xyz@gmail.com");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.email("xyz@gmail.com"));
    }

    @Test
    public void testSave() throws ConstraintViolationException,
            UnsupportedEncodingException, NoSuchAlgorithmException,
            ServiceException, DAOException {

        userService.setDAO(userDAO);
        userService.setDAO(subscriptionDAO);
        Subscriptions sub = new Subscriptions();
        UserDetails ud = new UserDetails();

        when(
                userDAO.save(ud, "abc")).thenReturn(true);
        when(userDAO.addUserRole("abc")).thenReturn(true);
        when(subscriptionDAO.getSub("basic")).thenReturn(
                Arrays.asList(sub));
        when(subscriptions.getSubid()).thenReturn(1);
        when(subscriptions.getSubDuration()).thenReturn(12);
        when(subscriptions.getMaxBooks()).thenReturn(5);
        when(subscriptionDAO.invalidateSub("abc")).thenReturn(true);
        when(subscriptionDAO.addSub("abc", 1, 12, 5, "basic"))
                .thenReturn(true);
        Boolean actual = userService.save(ud, "abc", "basic");
        assertEquals(true, actual);
    }

    @Test(expected = ServiceException.class)
    public void testUser() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        String username = "xyz";
        UserDetails ud = new UserDetails();

        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        List<UserDetails> actual = userService.user(username);
        assertEquals(Arrays.asList(ud), actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByUserName(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.user(username));
    }

    @Test(expected = ServiceException.class)
    public void testAddress() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        String username = "xyz";
        UserDetails ud = new UserDetails();

        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        String actual = userService.address(username);
        assertEquals(ud.getAddress(), actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByUserName(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.address(username));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testActiveUser() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        String username = "abc";
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
        UserDetails ud = new UserDetails();

        when(userDAO.activeUser()).thenReturn(Arrays.asList(us));
        when(userDAO.getUserDet(username)).thenReturn(Arrays.asList(ud));
        Map<String, List> expected = new HashMap<String, List>();
        expected.put("usersub", Arrays.asList(us));
        expected.put("userdet", Arrays.asList(ud));
        Map<String, List> actual = userService.activeUser();
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(userDAO).activeUser();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.activeUser());
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testUserReq() throws DAOException, ServiceException {

        userService.setDAO(userDAO);
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();
        UserRequest ur = new UserRequest();
        ur.setBookid(1);
        ur.setEndDate(endDate);
        ur.setReqAddress("user address");
        ur.setReqid(1L);
        ur.setReqStatus("close");
        ur.setReqType("delivery");
        ur.setStartDate(date);
        ur.setUsername("abc");

        UserDetails ud = new UserDetails();

        when(userDAO.userReq()).thenReturn(Arrays.asList(ur));
        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        Map<String, List> expected = new HashMap<String, List>();
        expected.put("users", Arrays.asList(ud));
        expected.put("userReq", Arrays.asList(ur));
        Map<String, List> actual = userService.userReq();
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(userDAO).userReq();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.userReq());
    }

    @Test(expected = ServiceException.class)
    public void testChngReqStat() throws ServiceException, DAOException {

        userService.setDAO(userDAO);
        int reqid = 1;

        when(userDAO.updtReqStat(reqid)).thenReturn(true);
        Boolean actual = userService.chngReqStat(reqid);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(userDAO).updtReqStat(reqid);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, userService.chngReqStat(reqid));
    }

}
