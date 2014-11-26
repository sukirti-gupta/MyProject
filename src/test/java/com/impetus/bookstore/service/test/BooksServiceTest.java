package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.service.BooksService;

public class BooksServiceTest {

    private BooksDAO booksDAO;
    BooksService booksService = new BooksService();
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    @Before
    public void setUp() {
        booksDAO = mock(BooksDAO.class);
    }

    @Test(expected = ServiceException.class)
    public void testFindAll() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        BookDetails bd = new BookDetails();
        BookDetails bd2 = new BookDetails();

        when(booksDAO.search("the")).thenReturn(Arrays.asList(bd, bd2));
        List<BookDetails> actual = booksService.search("the");
        assertEquals(Arrays.asList(bd, bd2), actual);

        doThrow(new DAOException("error")).when(booksDAO).search("abc");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.search("abc"));
    }

    @Test(expected = ServiceException.class)
    public void testRequestedBook() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        BookDetails bd = new BookDetails();

        when(booksDAO.requestedBook(1)).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = booksService.requestedBook(1);
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(booksDAO).requestedBook(3);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.requestedBook(3));
    }

    @Test(expected = ServiceException.class)
    public void testFindAdm() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        BookDetails bd = new BookDetails();

        when(booksDAO.findAdm("the")).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = booksService.findAdm("the");
        assertEquals(Arrays.asList(bd), actual);

        doThrow(new DAOException("error")).when(booksDAO).findAdm("book");
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.findAdm("book"));
    }

    @Test(expected = ServiceException.class)
    public void testDltbook() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);

        when(booksDAO.deleteBook(1)).thenReturn(true);
        Boolean actual = booksService.deleteBook(1);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(booksDAO).deleteBook(2);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.deleteBook(2));
    }

    @Test
    public void testUpdt() throws DAOException, ServiceException {
        
        Date date = new Date();
        booksService.setDAO(booksDAO);
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

        when(
                booksDAO.update("abc", "This is the description", bd)).thenReturn(true);
        Boolean actual = booksService.update(bd);
        assertEquals(true, actual);
    }

    @Test
    public void testAdd() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
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

        when(
                booksDAO.addBook("abc", "description", bd)).thenReturn(true);
        Boolean actual = booksService.add(bd);
        assertEquals(true, actual);
    }
    
    @Test
    public void testCallDAO() throws DAOException, InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException {
        booksService.setDAO(booksDAO);
        String str = "abc,def,xyz";
        String file = "csv";
        when(booksDAO.uploadCSV(str)).thenReturn(true);
        Boolean actual = booksService.uploadCsvToDatabse(str,file);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(booksDAO).uploadCSV(str);
        assertEquals(true, actual);
    }

    @Test(expected = ServiceException.class)
    public void testGetAuthors() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        List<String> expected = new ArrayList<String>();
        expected.add("author1");
        expected.add("author2");
        when(booksDAO.getAllAuthors()).thenReturn(expected);
        List<String> actual = booksService.getAuthors();
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).getAllAuthors();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.getAuthors());
    }

    @Test(expected = ServiceException.class)
    public void testGetCategory() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        List<String> expected = new ArrayList<String>();
        expected.add("category1");
        expected.add("category2");
        when(booksDAO.getAllCategory()).thenReturn(expected);
        List<String> actual = booksService.getCategory();
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).getAllCategory();
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.getCategory());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = ServiceException.class)
    public void testSummary() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        String auth = null;
        String cat = null;
        int bookid = 1;
        cal.add(Calendar.DATE, 20);
        Date from = new Date();
        Date to = cal.getTime();
        Map<String, List> expected = new HashMap<String, List>();
        List books = new ArrayList();
        books.add(1);

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
        bd.setPublisher("publisher");;

        when(booksDAO.summaryByDuration(from, to)).thenReturn(books);
        when(booksDAO.summaryByCategory(from, to, cat)).thenReturn(books);
        when(booksDAO.summaryByAuthor(from, to, auth)).thenReturn(books);
        when(booksDAO.fetchBookById(bookid)).thenReturn(Arrays.asList(bd));
        when(booksDAO.reqCount(bookid)).thenReturn(books);
        Map<String, List> actual = booksService.summary(from, to, auth, cat);
        expected.put("books", Arrays.asList(bd));
        expected.put("count", Arrays.asList(books));
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).summaryByDuration(from, to);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException,
                booksService.summary(from, to, auth, cat));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testSummaryBranch1() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        String auth = null;
        String cat = "category";
        int bookid = 1;
        cal.add(Calendar.DATE, 20);
        Date from = new Date();
        Date to = cal.getTime();
        Map<String, List> expected = new HashMap<String, List>();
        List books = new ArrayList();
        books.add(1);

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
        bd.setPublisher("publisher");;

        when(booksDAO.summaryByDuration(from, to)).thenReturn(books);
        when(booksDAO.summaryByCategory(from, to, cat)).thenReturn(books);
        when(booksDAO.summaryByAuthor(from, to, auth)).thenReturn(books);
        when(booksDAO.fetchBookById(bookid)).thenReturn(Arrays.asList(bd));
        when(booksDAO.reqCount(bookid)).thenReturn(books);
        Map<String, List> actual = booksService.summary(from, to, auth, cat);
        expected.put("books", Arrays.asList(bd));
        expected.put("count", Arrays.asList(books));
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testSummaryBranch2() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        String auth = "author";
        String cat = null;
        int bookid = 1;
        cal.add(Calendar.DATE, 20);
        Date from = new Date();
        Date to = cal.getTime();
        Map<String, List> expected = new HashMap<String, List>();
        List books = new ArrayList();
        books.add(1);

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
        bd.setPublisher("publisher");;

        when(booksDAO.summaryByDuration(from, to)).thenReturn(books);
        when(booksDAO.summaryByCategory(from, to, cat)).thenReturn(books);
        when(booksDAO.summaryByAuthor(from, to, auth)).thenReturn(books);
        when(booksDAO.fetchBookById(bookid)).thenReturn(Arrays.asList(bd));
        when(booksDAO.reqCount(bookid)).thenReturn(books);
        Map<String, List> actual = booksService.summary(from, to, auth, cat);
        expected.put("books", Arrays.asList(bd));
        expected.put("count", Arrays.asList(books));
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test(expected = ServiceException.class)
    public void testSummary2() throws DAOException, ServiceException {

        booksService.setDAO(booksDAO);
        int bookid = 1;
        cal.add(Calendar.DATE, 20);
        Date from = new Date();
        Date to = cal.getTime();
        Map<String, List> expected = new HashMap<String, List>();
        List books = new ArrayList();
        books.add(1);
        BookDetails bd = new BookDetails();

        when(booksDAO.summaryByDuration(from, to)).thenReturn(books);
        when(booksDAO.fetchBookById(bookid)).thenReturn(Arrays.asList(bd));
        when(booksDAO.getDeliveryCount(bookid)).thenReturn(books);
        when(booksDAO.getReturnCount(bookid)).thenReturn(books);
        when(booksDAO.getCancelCount(bookid)).thenReturn(books);
        Map<String, List> actual = booksService.summaryForRequestCount(from, to);
        expected.put("books", Arrays.asList(bd));
        expected.put("delivery", books);
        expected.put("return", books);
        expected.put("cancel", books);
        assertEquals(expected, actual);

        doThrow(new DAOException("error")).when(booksDAO).summaryByDuration(from, to);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(serviceException, booksService.summaryForRequestCount(from, to));
    }

}
