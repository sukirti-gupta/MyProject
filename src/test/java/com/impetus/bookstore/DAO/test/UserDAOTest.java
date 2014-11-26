package com.impetus.bookstore.DAO.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserRoles;
import com.impetus.bookstore.model.UserSubs;

public class UserDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query query;

    UserDAO userDAO = new UserDAO();

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
    }

    @Test
    public void testEmail() throws DAOException {

        String eid = "xyz@gmail.com";
        String hql = "from UserDetails where eid='" + eid + "'";

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        boolean actual = userDAO.email(eid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testEmailcatch() throws DAOException {
        boolean actual = userDAO.email("xyz@gmail.com");
    }

    @Test
    public void testEmailFail() throws DAOException {

        String eid = "xyz@gmail.com";
        String hql = "from UserDetails where eid='" + eid + "'";
        UserDetails ud = new UserDetails();

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(Arrays.asList(ud));
        boolean actual = userDAO.email(eid);
        assertEquals(false, actual);
    }

    @Test
    public void testStoreData() throws ConstraintViolationException,
    UnsupportedEncodingException, NoSuchAlgorithmException,
    DAOException {

        userDAO.setSessionFactory(sessionFactory);
        UserDetails ud = new UserDetails();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        boolean actual = userDAO.save(ud, "abc");
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testStoreDatacatch() throws DAOException, ConstraintViolationException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserDetails ud = new UserDetails();
        boolean actual = userDAO.save(ud, "abc");
    }

    @Test
    public void testAddUserRole() throws DAOException {

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        boolean actual = userDAO.addUserRole("abc");
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAddUserRolecatch() throws DAOException {
        boolean actual = userDAO.addUserRole("abc");
    }

    @Test
    public void testAllDet() throws DAOException {

        String username = "xyz";
        String hql = "from UserDetails where username='" + username + "'";

        UserDetails ud = new UserDetails();
        List<UserDetails> expected = new ArrayList<UserDetails>();
        expected.add(ud);

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserDetails> actual = userDAO.getUserByUserName(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAllDetcatch() throws DAOException {
        List<UserDetails> actual = userDAO.getUserByUserName("abc");
    }

    @Test
    public void testAllDet2() throws DAOException {

        String eid = "xyz@gmail.com";
        String hql = "from UserDetails where eid='" + eid + "'";

        UserDetails ud = new UserDetails();
        List<UserDetails> expected = new ArrayList<UserDetails>();
        expected.add(ud);

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserDetails> actual = userDAO.getUserByEmail(eid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAllDet2catch() throws DAOException {
        List<UserDetails> actual = userDAO.getUserByEmail("xyz@gmail.com");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testActiveUser() throws DAOException {

        Date today = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String hql = "from UserSubs where valid = '1' and end_date>='"
                + ft.format(today) + "'";

        UserSubs us = new UserSubs();
        List<UserSubs> expected = new ArrayList<UserSubs>();
        expected.add(us);

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserSubs> actual = userDAO.activeUser();
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testActiveUserCatch() throws DAOException {
        List<UserSubs> actual = userDAO.activeUser();
    }

    @Test
    public void testGetUserDet() throws DAOException {

        String username = "abc";
        String hql = "from UserDetails where username='" + username + "'";

        UserDetails ud = new UserDetails();
        List<UserDetails> expected = new ArrayList<UserDetails>();
        expected.add(ud);

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserDetails> actual = userDAO.getUserDet(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetUserDetCatch() throws DAOException {
        List<UserDetails> actual = userDAO.getUserDet("abc");
    }

    @Test
    public void testUserReq() throws DAOException {

        String hql = "from UserRequest where (req_type='delivery' or req_type='return') and (req_status='pending' or req_status='close')";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = userDAO.userReq();
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testUserReqCatch() throws DAOException {
        List<UserRequest> actual = userDAO.userReq();
    }

    @Test
    public void testUpdtReqStat() throws DAOException {

        int reqid = 1;
        String hql = "update UserRequest set req_status='close' where req_id='"
                + reqid + "'";

        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = userDAO.updtReqStat(reqid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testUpdtReqStatCatch() throws DAOException {
        Boolean actual = userDAO.updtReqStat(1);
    }

    @Test
    public void testgetAllAdmin() throws DAOException {
        String hql = "from UserRoles where role = 'ROLE_ADMIN'";
        List<UserRoles> expected = new ArrayList<UserRoles>();
        UserRoles ur = new UserRoles();
        ur.setIduserRoles(1);
        ur.setRole("ROLE_ADMIN");
        ur.setUsername("abc");
        expected.add(ur);
        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRoles> actual = userDAO.getAllAdmin();
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testgetAllAdminCatch() throws DAOException {
        List<UserRoles> actual = userDAO.getAllAdmin();
    }

    @Test
    public void testSaveAdmin() throws DAOException, ConstraintViolationException, UnsupportedEncodingException, NoSuchAlgorithmException{
        
        String password = "password";
        userDAO.setSessionFactory(sessionFactory);
        UserDetails ud = new UserDetails();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        boolean actual = userDAO.saveAdmin(ud, password);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testSaveAdminCatch() throws DAOException, Exception {
        UserDetails ud = new UserDetails();
        String password = "password";
        boolean actual = userDAO.save(ud, password);
    }
    
    @Test
    public void testAddAdminRole() throws DAOException{
        String username = "abc";
        userDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        boolean actual = userDAO.addAdminRole(username);
        assertEquals(true,actual);
    }
    
    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAddAdminRoleCatch() throws DAOException{
        String username = "abc";
        String password = "password";
        boolean actual = userDAO.addAdminRole(username);
    }
}
