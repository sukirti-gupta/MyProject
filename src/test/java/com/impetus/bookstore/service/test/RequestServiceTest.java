package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.dao.RequestDAO;
import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.RequestService;

public class RequestServiceTest {

    private BooksDAO booksDAO;
    private RequestDAO requestDAO;
    private SubscriptionDAO subscriptionDAO;
    RequestService requestService = new RequestService();
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        booksDAO = mock(BooksDAO.class);
        requestDAO = mock(RequestDAO.class);
        subscriptionDAO = mock(SubscriptionDAO.class);
    }

    @Test(expected = ServiceException.class)
    public void testPlaceRequest() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        String username = "abc";
        int bookid = 1;
        Date date = new Date();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();

        BookDetails bd = new BookDetails();
        bd.setAuthor("author");
        bd.setAvail(10);
        bd.setBookid(1);
        bd.setCategory("category");
        bd.setDateAdded(date);
        bd.setDescription("description");
        bd.setExist("true");
        bd.setImage("image");
        bd.setLanguage("language");
        bd.setName("name");
        bd.setPublisher("publisher");

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);
        List<UserRequest> ur = new ArrayList<UserRequest>();

        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(booksDAO.requestedBook(bookid)).thenReturn(Arrays.asList(bd));
        when(requestDAO.getDel(username, bookid)).thenReturn(ur);
        String actual = requestService.placeRequest(username, bookid);
        String expected = "confirm";
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(3);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.placeRequest(username, 3));
    }

    @Test
    public void testPlaceRequestBranch1() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        String actual = requestService.placeRequest("abc", 1);
        String expected = "subinvalid";
        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceRequestBranch2() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        Date endDate = new Date();
        cal.add(Calendar.DATE, 30);
        endDate = cal.getTime();
        UserSubs us = new UserSubs();
        us.setBooksLeft(0);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);
        when(subscriptionDAO.getUserSub("abc")).thenReturn(Arrays.asList(us));
        String actual = requestService.placeRequest("abc", 1);
        String expected = "nobooksleft";
        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceRequestBranch3() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        int bookid = 1;
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
        List<UserRequest> ur = new ArrayList<UserRequest>();

        when(subscriptionDAO.getUserSub(username))
                .thenReturn(Arrays.asList(us));
        when(requestDAO.getDel(username, bookid)).thenReturn(ur);
        String actual = requestService.placeRequest(username, bookid);
        String expected = "notavailable";
        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceRequestBranch4() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(requestDAO);
        requestService.setDAO(booksDAO);

        int bookid = 1;
        String username = "abc";
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();
        UserRequest ur = new UserRequest();
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
        when(requestDAO.getDel(username, bookid)).thenReturn(Arrays.asList(ur));
        String actual = requestService.placeRequest(username, bookid);
        String expected = "bookexist";
        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceRequestBranch5() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        String actual = requestService.placeRequest(null, 1);
        String expected = "log in";
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void testStoreBook() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        requestService.setDAO(subscriptionDAO);

        BookDetails bd = new BookDetails();
        UserSubs us = new UserSubs();

        when(requestDAO.saveRequest("abc", 1, "xyz city")).thenReturn(true);
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.decBookCount(1)).thenReturn(true);
        when(subscriptionDAO.getUserSub("abc")).thenReturn(Arrays.asList(us));
        when(subscriptionDAO.decUserSub("abc")).thenReturn(true);
        Boolean actual = requestService.saveRequest("abc", 1, "xyz city");
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(3);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException,
                requestService.saveRequest("xyz", 3, "xyz city"));
    }

    @Test(expected = ServiceException.class)
    public void testReturnBook() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        when(requestDAO.returnBook("abc", 1)).thenReturn(true);
        when(booksDAO.incBookCount(1)).thenReturn(true);
        Boolean actual = requestService.returnBook("abc", 1);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(requestDAO)
                .returnBook("abc", 1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.returnBook("abc", 1));
    }

    @Test(expected = ServiceException.class)
    public void testCancelReturn() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        UserSubs us = new UserSubs();
        BookDetails bd = new BookDetails();

        when(subscriptionDAO.getUserSub("abc")).thenReturn(Arrays.asList(us));
        when(requestDAO.cancelReturn("abc", 1)).thenReturn(true);
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.decBookCount(1)).thenReturn(true);
        String actual = requestService.cancelReturn("abc", 1);
        String expected = "Return request cancelled !";
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(3);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.cancelReturn("abc", 3));
    }

    @Test(expected = ServiceException.class)
    public void testCancelReturnCatch() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        UserSubs us = new UserSubs();
        BookDetails bd = new BookDetails();

        when(subscriptionDAO.getUserSub("abc")).thenReturn(Arrays.asList(us));
        when(requestDAO.cancelReturn("abc", 1)).thenReturn(true);
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.decBookCount(1)).thenReturn(true);
        doThrow(new DAOException("error")).when(booksDAO).requestedBook(3);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.cancelReturn("abc", 3));
    }

    @Test
    public void testCancelReturnBranch() throws DAOException, ServiceException {

        requestService.setDAO(subscriptionDAO);
        String actual = requestService.cancelReturn("abc", 1);
        String expected = "Your subscription plan needs to be updated for this request.";
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void testCancelDelivery() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        when(requestDAO.cancelDelivery("abc", 1)).thenReturn(true);
        when(booksDAO.incBookCount(1)).thenReturn(true);
        Boolean actual = requestService.cancelDelivery("abc", 1);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(requestDAO).cancelDelivery(
                "abc", 1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.cancelDelivery("abc", 1));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = ServiceException.class)
    public void testAddToBookshelf() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        List empty = new ArrayList();

        when(requestDAO.getBookshelf("abc", 1)).thenReturn(empty);
        when(requestDAO.addToBookshelf("abc", 1)).thenReturn(true);
        String actual = requestService.addToBookshelf("abc", 1);
        String expected = "done";
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(requestDAO).getBookshelf("abc",
                1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.addToBookshelf("abc", 1));
    }

    @Test
    public void testAddToBookshelf2() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.getBookshelf("abc", 1)).thenReturn(Arrays.asList(ur));
        when(requestDAO.addToBookshelf("abc", 1)).thenReturn(true);
        String actual = requestService.addToBookshelf("abc", 1);
        String expected = "bookexist";
        assertEquals(expected, actual);
    }

    @Test
    public void testAddToBookshelfBranch1() throws ServiceException,
            DAOException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        String actual = requestService.addToBookshelf(null, 1);
        String expected = "log in";
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void testRemoveBookshelf() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);

        when(requestDAO.removeBookshelf("abc", 1)).thenReturn(true);
        Boolean actual = requestService.removeBookshelf("abc", 1);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(requestDAO).removeBookshelf(
                "abc", 1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.removeBookshelf("abc", 1));
    }

    @Test(expected = ServiceException.class)
    public void testGetReqDetails() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.getReqDetails("abc")).thenReturn(Arrays.asList(ur));
        List<UserRequest> actual = requestService.getReqDetails("abc");
        assertEquals(Arrays.asList(ur), actual);

        doThrow(new DAOException("error")).when(requestDAO)
                .getReqDetails("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.getReqDetails("abc"));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testShowBookshelf() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();

        BookDetails bd = new BookDetails();
        UserRequest ur = new UserRequest();
        ur.setBookid(1);
        ur.setEndDate(endDate);
        ur.setReqAddress("user address");
        ur.setReqid(1L);
        ur.setReqStatus("close");
        ur.setReqType("delivery");
        ur.setStartDate(date);
        ur.setUsername("abc");

        when(requestDAO.bookshelf("abc")).thenReturn(
                Arrays.asList(ur.getBookid()));
        when(booksDAO.fetchBookById(1)).thenReturn(Arrays.asList(bd));
        List actual = requestService.showBookshelf("abc");
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(requestDAO).bookshelf("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.showBookshelf("abc"));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ServiceException.class)
    public void testuserReq1() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.booksHolding("abc")).thenReturn(Arrays.asList(ur));
        List<UserRequest> actual = requestService.booksHolding("abc");
        assertEquals(Arrays.asList(ur), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksHolding("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.booksHolding("abc"));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testShowCurrent() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
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
        BookDetails bd = new BookDetails();

        when(requestDAO.booksHolding("abc")).thenReturn(Arrays.asList(ur));
        when(booksDAO.fetchBookById(1)).thenReturn(Arrays.asList(bd));
        List actual = requestService.showCurrent("abc");
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksHolding("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.showCurrent("abc"));

    }

    @SuppressWarnings("unchecked")
    @Test(expected = ServiceException.class)
    public void testuserReq2() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.booksToReturn("abc")).thenReturn(Arrays.asList(ur));
        List<UserRequest> actual = requestService.booksToReturn("abc");
        assertEquals(Arrays.asList(ur), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksToReturn("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.booksToReturn("abc"));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testToReturn() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();

        BookDetails bd = new BookDetails();
        UserRequest ur = new UserRequest();
        ur.setBookid(1);
        ur.setEndDate(endDate);
        ur.setReqAddress("user address");
        ur.setReqid(1L);
        ur.setReqStatus("close");
        ur.setReqType("delivery");
        ur.setStartDate(date);
        ur.setUsername("abc");

        when(requestDAO.booksToReturn("abc")).thenReturn(Arrays.asList(ur));
        when(booksDAO.fetchBookById(1)).thenReturn(Arrays.asList(bd));
        List actual = requestService.toReturn("abc");
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksToReturn("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.toReturn("abc"));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ServiceException.class)
    public void testuserReq3() throws DAOException, ServiceException {
        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.booksToDeliver("abc")).thenReturn(Arrays.asList(ur));
        List<UserRequest> actual = requestService.booksToDeliver("abc");
        assertEquals(Arrays.asList(ur), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksToDeliver("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.booksToDeliver("abc"));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testToDeliver() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
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

        BookDetails bd = new BookDetails();

        when(requestDAO.booksToDeliver("abc")).thenReturn(Arrays.asList(ur));
        when(booksDAO.fetchBookById(1)).thenReturn(Arrays.asList(bd));
        List actual = requestService.toDeliver("abc");
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksToDeliver("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.toDeliver("abc"));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ServiceException.class)
    public void testuserReq4() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        UserRequest ur = new UserRequest();

        when(requestDAO.booksReturned("Abc")).thenReturn(Arrays.asList(ur));
        List<UserRequest> actual = requestService.booksReturned("Abc");
        assertEquals(Arrays.asList(ur), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksReturned("Abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.booksReturned("Abc"));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = ServiceException.class)
    public void testReturned() throws DAOException, ServiceException {

        requestService.setDAO(booksDAO);
        requestService.setDAO(requestDAO);
        String username = "abc";
        int bookid = 1;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();

        BookDetails bd = new BookDetails();
        UserRequest ur = new UserRequest();
        ur.setBookid(1);
        ur.setEndDate(endDate);
        ur.setReqAddress("user address");
        ur.setReqid(1L);
        ur.setReqStatus("close");
        ur.setReqType("delivery");
        ur.setStartDate(date);
        ur.setUsername("abc");

        when(requestDAO.booksReturned(username)).thenReturn(Arrays.asList(ur));
        when(booksDAO.fetchBookById(bookid)).thenReturn(Arrays.asList(bd));
        List actual = requestService.returned(username);
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(requestDAO).booksReturned(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, requestService.returned(username));
    }
}
