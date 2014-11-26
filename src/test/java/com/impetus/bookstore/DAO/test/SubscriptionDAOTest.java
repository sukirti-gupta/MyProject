package com.impetus.bookstore.DAO.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserSubs;

public class SubscriptionDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query query;
    SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
    Date date = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);

    }

    @Test
    public void testGetSub() throws DAOException {

        String hql = "from Subscriptions where validity='1'";

        List<Subscriptions> expected = new ArrayList<Subscriptions>();
        Subscriptions sub = new Subscriptions();
        expected.add(sub);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<Subscriptions> actual = subscriptionDAO.getSub();
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetSubcatch() throws DAOException {
        List<Subscriptions> actual = subscriptionDAO.getSub();
    }

    @Test
    public void testGetSub2() throws DAOException {

        String subscription = "basic";
        String hql = "from Subscriptions where sub_name = '" + subscription
                + "'";

        Subscriptions sub = new Subscriptions();
        List<Subscriptions> expected = new ArrayList<Subscriptions>();
        expected.add(sub);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<Subscriptions> actual = subscriptionDAO.getSub(subscription);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetSub2catch() throws DAOException {
        List<Subscriptions> actual = subscriptionDAO.getSub("basic");
    }

    @Test
    public void testSubDetails() throws DAOException {

        int sub_id = 1;
        String hql = "from Subscriptions where sub_id='" + sub_id + "'";

        Subscriptions sub = new Subscriptions();
        List<Subscriptions> expected = new ArrayList<Subscriptions>();
        expected.add(sub);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<Subscriptions> actual = subscriptionDAO.subDetails(sub_id);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testSubDetailscatch() throws DAOException {
        List<Subscriptions> actual = subscriptionDAO.subDetails(1);
    }

    @Test
    public void testUserSub() throws DAOException {

        String username = "abc";
        String hql = "from UserSubs where username='" + username
                + "' and valid='1'";

        UserSubs us = new UserSubs();
        List<UserSubs> expected = new ArrayList<UserSubs>();
        expected.add(us);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserSubs> actual = subscriptionDAO.userSub(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testUserSubcatch() throws DAOException {
        List<UserSubs> actual = subscriptionDAO.userSub("abc");
    }

    @Test
    public void testGetUserSub() throws DAOException {

        String username = "abc";
        String hql = "from UserSubs where username='" + username
                + "' and valid='1'";

        UserSubs us = new UserSubs();
        List<UserSubs> expected = new ArrayList<UserSubs>();
        expected.add(us);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserSubs> actual = subscriptionDAO.getUserSub(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetUserSubcatch() throws DAOException {
        List<UserSubs> actual = subscriptionDAO.getUserSub("abc");
    }

    @Test
    public void testGetActvUser() throws DAOException {

        String hql = "from UserSubs where valid='1'";

        UserSubs us = new UserSubs();
        List<UserSubs> expected = new ArrayList<UserSubs>();
        expected.add(us);

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserSubs> actual = subscriptionDAO.getActvUser();
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetActvUsercatch() throws DAOException {
        List<UserSubs> actual = subscriptionDAO.getActvUser();
    }

    @Test
    public void testAddSub() throws DAOException {

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Boolean actual = subscriptionDAO.addSub("abc", 1, 12, 5, "basic");
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAddSubcatch() throws DAOException {
        Boolean actual = subscriptionDAO.addSub("abc", 1, 12, 5, "basic");
    }

    @Test
    public void testInvalidateSub() throws DAOException {

        String username = "abc";
        String hql = "update UserSubs set valid='0' where (username='"
                + username + "' and valid='1')";

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = subscriptionDAO.invalidateSub(username);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testInvalidateSubcatch() throws DAOException {
        Boolean actual = subscriptionDAO.invalidateSub("abc");
    }

    @Test
    public void testDecUserSub() throws DAOException {

        String username = "abc";
        String hql = "update UserSubs set books_left= books_left-1"
                + " where username='" + username + "'" + "and valid='1'";

        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = subscriptionDAO.decUserSub(username);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testDecUserSubcatch() throws DAOException {
        Boolean actual = subscriptionDAO.decUserSub("abc");
    }
    
    @Test
    public void testSaveSubscription() throws DAOException{
        Subscriptions sub = new Subscriptions();
        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        boolean actual = subscriptionDAO.saveSubscription(sub);
        assertEquals(true, actual);
    }
    
    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testSaveSubscriptionCatch() throws DAOException{
        Subscriptions sub = new Subscriptions();
        boolean actual = subscriptionDAO.saveSubscription(sub);
    }
    
    @Test
    public void testUpdateSubscription() throws DAOException{
        Subscriptions subs = new Subscriptions();
        subs.setMaxBooks(5);
        subs.setSubAmount(100);
        subs.setSubDuration(12);
        subs.setSubid(1);
        subs.setSubName("Basic");
        subs.setValidity(1);
        
        int subid = 1;
        String hql = "update Subscriptions set sub_name='"
                + subs.getSubName() + "', sub_amount='" + subs.getSubAmount()
                + "', sub_duration='" + subs.getSubDuration()
                + "', max_books='" + subs.getMaxBooks() + "', validity='"
                + subs.getValidity() + "' where sub_id='"+subid+"'";
        subscriptionDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        boolean actual = subscriptionDAO.updateSubscription(1, subs);
        assertEquals(true, actual);
    }
    
    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testUpdateSubscriptionCatch() throws DAOException{
        Subscriptions sub = new Subscriptions();
        boolean actual = subscriptionDAO.updateSubscription(1, sub);
    }
    
    @Test(expected = DAOException.class)
    public void testGetValidSub() throws DAOException {
        Subscriptions sub = new Subscriptions();
        sub.setMaxBooks(5);
        sub.setSubAmount(100);
        sub.setSubDuration(12);
        sub.setSubid(1);
        sub.setSubName("Basic");
        sub.setValidity(1);
        
        List<Subscriptions> expected = new ArrayList<Subscriptions>();
        expected.add(sub);
        String subscription = "Basic";
        String hql = "from Subscriptions where sub_name = '"
                + subscription + "' and validity='1'";
        
        subscriptionDAO.setSessionFactory(sessionFactory);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(Arrays.asList(sub));
        List<Subscriptions> actual = subscriptionDAO.getValidSub(subscription);
        assertEquals(expected, actual);
    }

}
