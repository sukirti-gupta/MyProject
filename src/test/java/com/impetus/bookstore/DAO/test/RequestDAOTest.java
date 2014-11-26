package com.impetus.bookstore.DAO.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.RequestDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.UserRequest;

public class RequestDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query query;
    private SQLQuery sqlquery;
    RequestDAO requestDAO = new RequestDAO();
    private String fromQuery = "from UserRequest where username ='";

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
        sqlquery = mock(SQLQuery.class);
    }

    @Test
    public void teststoreBook() throws DAOException {

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Boolean actual = requestDAO.saveRequest("abc", 1, "xyz city");
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void teststoreBookCatch() throws DAOException {
        Boolean actual = requestDAO.saveRequest("abc", 1, "xyz city");
    }

    @Test
    public void testgetDel() throws DAOException {

        String username = "abc";
        int bookid = 1;
        String hql = fromQuery
                + username
                + "'"
                + "and book_id='"
                + bookid
                + "'"
                + "and ((req_type='delivery' and (req_status='pending' or req_status='close')) or (req_type='return' and req_status='pending'))";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.getDel(username, bookid);
        assertEquals(expected, actual);

    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testgetDelCatch() throws DAOException {
        List<UserRequest> actual = requestDAO.getDel("xyz", 1);
    }

    @Test
    public void testGetBookshelf() throws DAOException {

        String username = "abc";
        int bookid = 1;
        String hql = fromQuery + username + "'"
                + "and book_id='" + bookid + "'"
                + "and req_type='bookshelf'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.getBookshelf(username, bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetBookshelfCatch() throws DAOException {
        List<UserRequest> actual = requestDAO.getBookshelf("xyz", 3);
    }

    @Test
    public void testReturnBook() throws DAOException {

        String username = "abc";
        int bookid = 1;
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String hql = "update UserRequest set req_type='return', req_status='pending', end_date='"
                + ft.format(date)
                + "'"
                + " where username ='"
                + username
                + "' and book_id='"
                + bookid
                + "' and req_type='delivery' and req_status='close'";

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = requestDAO.returnBook(username, bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testReturnBookCatch() throws DAOException {
        Boolean actual = requestDAO.returnBook("xyz", 3);
    }

    @Test
    public void testCancelReturn() throws DAOException {

        String username = "abc";
        int bookid = 1;
        String hql = "update UserRequest set req_status='cancel' "
                + " where username ='" + username + "'"
                + " and book_id='" + bookid + "'"
                + " and req_type= 'return'"
                + "and req_status = 'pending'";

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = requestDAO.cancelReturn(username, bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testCancelReturnCatch() throws DAOException {
        Boolean actual = requestDAO.cancelReturn("xyz", 3);
    }

    @Test
    public void testCancelDelivery() throws DAOException {

        String username = "abc";
        int bookid = 1;
        String hql = "update UserRequest set req_status = 'cancel' "
                + " where username ='"
                + username
                + "'"
                + " and book_id='"
                + bookid
                + "'"
                + " and req_type= 'delivery'"
                + " and req_status = 'pending'";

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = requestDAO.cancelDelivery(username, bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testCancelDeliveryCatch() throws DAOException {
        Boolean actual = requestDAO.cancelDelivery("xyz", 3);
    }

    @Test
    public void testAddToBookshelf() throws DAOException {
        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Boolean actual = requestDAO.addToBookshelf("abc", 1);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testAddToBookshelfCatch() throws DAOException {
        Boolean actual = requestDAO.addToBookshelf("xyz", 3);
    }

    @Test
    public void testRemoveBookshelf() throws DAOException {

        String username = "abc";
        int bookid = 1;
        String hql = "delete from UserRequest " + " where username='"
                + username + "'" + " and book_id='" + bookid + "'"
                + " and req_type= 'bookshelf'";

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = requestDAO.removeBookshelf(username, bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testRemoveBookshelfCatch() throws DAOException {
        Boolean actual = requestDAO.removeBookshelf("xyz", 3);
    }

    @Test
    public void testGetReqDetails() throws DAOException {

        String username = "abc";
        String hql = fromQuery + username + "'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.getReqDetails(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testGetReqDetailsCatch() throws DAOException {
        List<UserRequest> actual = requestDAO.getReqDetails("xyz");
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testShowBookshelf() throws DAOException {

        String username = "abc";
        String sql = "select book_id from UserRequest where username='"
                + username + "' and req_type='bookshelf'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = requestDAO.bookshelf(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testShowBookshelfCatch() throws DAOException {
        List actual = requestDAO.bookshelf("xyz");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testuserReq1() throws DAOException {

        String username = "abc";
        String hql = fromQuery
                + username
                + "'"
                + " and ((req_type= 'delivery' and req_status= 'close') or (req_type='return' and req_status='cancel'))";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.booksHolding(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testuserReq1Catch() throws DAOException {
        List<UserRequest> actual = requestDAO.booksHolding("xyz");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testuserReq2() throws DAOException {

        String username = "abc";
        String hql = fromQuery
                + username
                + "'"
                + "and req_type= 'return' and req_status= 'pending'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.booksToReturn(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testuserReq2Catch() throws DAOException {
        List<UserRequest> actual = requestDAO.booksToReturn("xyz");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testuserReq3() throws DAOException {

        String username = "abc";
        String hql = fromQuery
                + username
                + "'"
                + "and req_type= 'delivery' and req_status= 'pending'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.booksToDeliver(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testuserReq3Catch() throws DAOException {
        List<UserRequest> actual = requestDAO.booksToDeliver("xyz");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testuserReq4() throws DAOException {

        String username = "abc";
        String hql = fromQuery
                + username
                + "'"
                + " and req_type= 'return' and req_status= 'close'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        requestDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<UserRequest> actual = requestDAO.booksReturned(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testuserReq4Catch() throws DAOException {
        List<UserRequest> actual = requestDAO.booksReturned("xyz");
    }

}
