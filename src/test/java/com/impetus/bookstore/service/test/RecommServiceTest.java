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
import com.impetus.bookstore.dao.RecommendationDAO;
import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.NewAndPopular;
import com.impetus.bookstore.model.Recommendations;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.RecommService;

public class RecommServiceTest {

    private BooksDAO booksDAO;
    private SubscriptionDAO subscriptionDAO;
    private RecommendationDAO recommendationDAO;
    private UserDAO userDAO;
    RecommService recommService = new RecommService();
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        booksDAO = mock(BooksDAO.class);
        subscriptionDAO = mock(SubscriptionDAO.class);
        recommendationDAO = mock(RecommendationDAO.class);
        userDAO = mock(UserDAO.class);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = ServiceException.class)
    public void testNewPopular() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        Date date = new Date();
        String newb = "1,2,3";
        String popb = "4,5,6";
        List bookid = new ArrayList();
        bookid.add(1);
        bookid.add(2);

        when(recommendationDAO.getnewbooks(date)).thenReturn(bookid);
        when(recommendationDAO.getpopular()).thenReturn(bookid);
        when(recommendationDAO.savenewpop(newb, popb)).thenReturn(true);
        Boolean actual = recommService.newPopular();
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(recommendationDAO).getpopular();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.newPopular());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = ServiceException.class)
    public void testRecommendations() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(subscriptionDAO);
        recommService.setDAO(booksDAO);

        Date date = new Date();
        cal.add(Calendar.DATE, 10);
        Date endDate = cal.getTime();
        String username = "abc";
        String rec = "7,8,9";
        List author = new ArrayList();
        author.add("Author1");
        List category = new ArrayList();
        category.add("Category1");
        List language = new ArrayList();
        language.add("language");

        BookDetails bd = new BookDetails();
        BookDetails bd2 = new BookDetails();
        BookDetails bd3 = new BookDetails();
        BookDetails bd4 = new BookDetails();
        BookDetails bd5 = new BookDetails();
        BookDetails bd6 = new BookDetails();
        BookDetails bd7 = new BookDetails();

        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(1);
        us.setValid(true);;

        when(subscriptionDAO.getActvUser()).thenReturn(Arrays.asList(us));
        when(recommendationDAO.favcategories(username)).thenReturn(
                Arrays.asList(category));
        when(recommendationDAO.favlanguages(username)).thenReturn(
                Arrays.asList(language));
        when(recommendationDAO.favauthors(username)).thenReturn(
                Arrays.asList(author));
        when(booksDAO.getAllCategory()).thenReturn(Arrays.asList(category));
        when(booksDAO.getAllAuthors()).thenReturn(Arrays.asList(author));
        when(booksDAO.fetchbooks(author.get(0).toString())).thenReturn(
                Arrays.asList(bd, bd2, bd3, bd4, bd5, bd6, bd7));
        when(recommendationDAO.saveRecomm(username, rec)).thenReturn(true);
        Boolean actual = recommService.recommendations();
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(recommendationDAO)
                .favcategories(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.recommendations());
    }

    @Test
    public void testRandomrecomm() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);

        BookDetails bd = new BookDetails();

        when(recommendationDAO.getnewbooks(date)).thenReturn(Arrays.asList(bd));
        when(recommendationDAO.saveRecomm("abc", "1,2,3,4,5,6"))
                .thenReturn(true);
        Boolean actual = recommService.randomrecomm("abc");
        assertEquals(true, actual);
    }

    @Test(expected = ServiceException.class)
    public void testRecombooks() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(booksDAO);
        recommService.setDAO(userDAO);
        String username = "abc";

        Recommendations rc = new Recommendations();
        rc.setId(1);
        rc.setRecommendations("1,2,3,4,5,6");
        rc.setUsername("abc");

        BookDetails bd = new BookDetails();
        UserDetails ud = new UserDetails();

        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(2)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(3)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(4)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(5)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(6)).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = recommService.recombooks(username);
        assertEquals(Arrays.asList(bd, bd, bd, bd, bd, bd), actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByUserName(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.recombooks(username));
    }

    @Test
    public void testRecombooksBranch() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(booksDAO);
        recommService.setDAO(userDAO);
        String username = "abc";
        List<BookDetails> bd = new ArrayList<BookDetails>();
        List<BookDetails> actual = recommService.recombooks(username);
        assertEquals(bd, actual);
    }

    @Test(expected = Exception.class)
    public void testRecombooksCatch() throws NumberFormatException,
            ServiceException, DAOException {

        recommService.setDAO(recommendationDAO);
        String username = "abc";

        doThrow(new Exception()).when(userDAO).getUserByUserName(username);
        Exception exception = new Exception();
        assertEquals(exception, recommService.recombooks(username));
    }

    @Test(expected = ServiceException.class)
    public void testRecombooksCatch2() throws NumberFormatException,
            ServiceException, DAOException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(booksDAO);

        Recommendations rc = new Recommendations();
        rc.setId(1);
        rc.setRecommendations("1,2,3,4,5,6");
        rc.setUsername("abc");

        BookDetails bd = new BookDetails();
        UserDetails ud = new UserDetails();

        when(userDAO.getUserByUserName("abc")).thenReturn(Arrays.asList(ud));
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(2)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(3)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(4)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(5)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(6)).thenReturn(Arrays.asList(bd));

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.recombooks("abc"));
    }

    @Test(expected = ServiceException.class)
    public void testNewbook() throws DAOException, ServiceException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(booksDAO);

        NewAndPopular np = new NewAndPopular();
        np.setDate(date);
        np.setNewbooks("1,2,3,4,5,6");
        np.setPopular("1,2,3,4,5,6");

        BookDetails bd = new BookDetails();

        when(recommendationDAO.newPopular()).thenReturn(Arrays.asList(np));
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(2)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(3)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(4)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(5)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(6)).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = recommService.newbook();
        assertEquals(Arrays.asList(bd, bd, bd, bd, bd, bd), actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.newbook());
    }

    @Test(expected = Exception.class)
    public void testNewbookCatch() throws NumberFormatException,
            ServiceException, DAOException {

        recommService.setDAO(recommendationDAO);

        doThrow(new Exception()).when(recommendationDAO)
                .newPopular();
        Exception exception = new Exception();
        assertEquals(exception, recommService.newbook());
    }

    @Test(expected = ServiceException.class)
    public void testPopular() throws NumberFormatException, ServiceException,
            DAOException {

        recommService.setDAO(recommendationDAO);
        recommService.setDAO(booksDAO);

        NewAndPopular np = new NewAndPopular();
        np.setDate(date);
        np.setNewbooks("1,2,3,4,5,6");
        np.setPopular("1,2,3,4,5,6");

        BookDetails bd = new BookDetails();

        when(recommendationDAO.newPopular()).thenReturn(Arrays.asList(np));
        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(2)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(3)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(4)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(5)).thenReturn(Arrays.asList(bd));
        when(booksDAO.requestedBook(6)).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = recommService.popular();
        assertEquals(Arrays.asList(bd, bd, bd, bd, bd, bd), actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(1);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, recommService.popular());
    }

    @Test(expected = Exception.class)
    public void testPopularCatch() throws NumberFormatException,
            ServiceException, DAOException {

        recommService.setDAO(recommendationDAO);

        doThrow(new Exception()).when(recommendationDAO)
                .newPopular();
        Exception exception = new Exception();
        assertEquals(exception, recommService.popular());
    }

}
