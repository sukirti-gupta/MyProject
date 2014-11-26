package com.impetus.bookstore.DAO.test;

import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.UserRequest;

import static org.junit.Assert.*;

public class BooksDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query query;
    private SQLQuery sqlquery;
    BooksDAO booksDAO = new BooksDAO();

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
        sqlquery = mock(SQLQuery.class);
    }

    @Test
    public void testFindAll() throws DataAccessException, DAOException {

        String book = "the";
        String hql = "from BookDetails where (name like '%' || '" + book
                + "' || '%' or author like '%' || '" + book + "' || '%'"
                + " or category like '%' || '" + book
                + "' || '%') and exist='true' order by dateAdded desc";

        BookDetails bd = new BookDetails();
        BookDetails bd2 = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);
        expected.add(bd2);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.search(book);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testFindAllcatch() throws DAOException {
        List<BookDetails> actual = booksDAO.search("title");
    }

    @Test
    public void testRequestedBook() throws DataAccessException, DAOException {

        int bookid = 1;
        String hql = "from BookDetails where book_id='" + bookid + "'";

        BookDetails bd = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.requestedBook(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testRequestedBookcatch() throws DAOException {
        List<BookDetails> actual = booksDAO.requestedBook(1);
    }

    @Test
    public void testdecBookDet() throws DAOException {

        int bookid = 1;
        String hql = "update BookDetails set avail= avail-1"
                + " where book_id='" + bookid + "'";

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        assertEquals(true, booksDAO.decBookCount(bookid));
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testdecBookDetCatch() throws DAOException {
        Boolean actual = booksDAO.decBookCount(1);
    }

    @Test
    public void testIncBookDet() throws DAOException {

        int bookid = 1;
        String hql = "update BookDetails set avail= avail+1"
                + " where book_id='" + bookid + "'";

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = booksDAO.incBookCount(bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testIncBookDetCatch() throws DAOException {
        Boolean actual = booksDAO.incBookCount(3);
    }

    @Test
    public void testFindAdm() throws DAOException {

        String book = "the";
        String hql = "from BookDetails where (name like '%' || '" + book
                + "' || '%' or author like '%' || '" + book + "' || '%'"
                + " or category like '%' || '" + book + "' || '%')";

        BookDetails bd = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.findAdm(book);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testFindAdmCatch() throws DAOException {
        List<BookDetails> actual = booksDAO.findAdm("var");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetDesc() throws DAOException {

        int bookid = 1;
        String sql = "select b.description from BookDetails b where b.book_id='"
                + bookid + "'";

        BookDetails bd = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.getDesc(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testGetDescCatch() throws DAOException {
        List<BookDetails> actual = booksDAO.getDesc(1);
    }

    @Test
    public void testAddBook() throws DAOException {
        Date date = new Date();
        booksDAO.setSessionFactory(sessionFactory);
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
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Boolean actual = booksDAO.addBook("abc", "description", bd);
        assertEquals(true, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testAddBookCatch() throws DAOException {
        Date date = new Date();
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
        Boolean actual = booksDAO.addBook("abc", "description", bd);
    }

    @Test
    public void testUpdt() throws DAOException {

        Date date = new Date();
        String name = "Book";
        String desc = "description";
        BookDetails bd = new BookDetails();
        bd.setAuthor("author1");
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
        String hql = "update BookDetails set name='"
                + name + "', author='" + bd.getAuthor() + "', publisher='"
                + bd.getPublisher() + "', category='" + bd.getCategory() + "',"
                + "language='" + bd.getLanguage() + "', description='" + desc
                + "', avail='" + bd.getAvail() + "', image='" + bd.getImage()
                + "', exist='" + bd.getExist() + "'" + " where book_id='" + bd.getBookid()
                + "'";

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = booksDAO.update(name, desc, bd);
        assertEquals(true, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testUpdtCatch() throws DAOException {
        Date date = new Date();
        BookDetails bd = new BookDetails();
        Boolean actual = booksDAO.update("abc", "This is the description", bd);
    }

    @Test
    public void testDltbook() throws DAOException {

        int bookid = 1;
        String hql = "update BookDetails set exist='false' where book_id = '"
                + bookid + "'";

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = booksDAO.deleteBook(bookid);
        assertEquals(true, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testDltbookCatch() throws DAOException {
        Boolean actual = booksDAO.deleteBook(1);
    }

    @Test
    public void testCallproc() throws DAOException {

        booksDAO.setSessionFactory(sessionFactory);
        String str = "1,Book,Author,Publisher,Category,Language,Description,Image,5,true,2014-10-01";
        String sql = "call crud_books('" + str + "')";

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        boolean actual = booksDAO.uploadCSV(str);
        assertEquals(true, actual);
    }

    @Test(expected = DAOException.class)
    public void testCallprocCatch() throws DAOException {
        String str = "1,Book,Author,Publisher,Category,Language,Description,Image,5,true,2014-10-01";
        String sql = "call crud_books('" + str + ")";

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        boolean actual = booksDAO.uploadCSV(str);
        assertEquals(true, actual);

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testAuthor() throws DAOException {

        String hql = "select distinct author from BookDetails order by author";

        BookDetails bd = new BookDetails();
        bd.setAuthor("Author1");
        BookDetails bd2 = new BookDetails();
        bd2.setAuthor("Author2");
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);
        expected.add(bd2);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(hql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = booksDAO.getAllAuthors();
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testAuthorCatch() throws DAOException {
        List actual = booksDAO.getAllAuthors();
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testCategory() throws DAOException {

        String sql = "select distinct category from BookDetails order by category";
        BookDetails bd = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = booksDAO.getAllCategory();
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testCategoryCatch() throws DAOException {
        List actual = booksDAO.getAllCategory();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testDuration() throws DAOException {
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id and r.start_date between"
                + "'"
                + ft.format(from)
                + "' and '"
                + ft.format(to)
                + "' group by book_id";

        BookDetails bd = new BookDetails();
        List expected = new ArrayList();
        expected.add(bd);

        UserRequest ur = new UserRequest();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.summaryByDuration(from, to);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testDurationCatch() throws DAOException {
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        List<BookDetails> actual = booksDAO.summaryByDuration(from, to);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testCatDateDateString() throws DAOException {

        String cat = "fiction";
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id "
                + "and r.start_date between '"
                + ft.format(from)
                + "' and '"
                + ft.format(to)
                + "' "
                + "and b.category like :category group by book_id";

        List expected = new ArrayList();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        UserRequest ur = new UserRequest();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.setParameter("category", "%" + cat + "%")).thenReturn(
                sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.summaryByCategory(from, to, cat);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testCatDateDateStringCatch() throws DAOException {
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        List<BookDetails> actual = booksDAO.summaryByCategory(from, to, "fiction");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testAuthDateDateString() throws DAOException {

        String auth = "author";
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id "
                + "and r.start_date between '"
                + ft.format(from)
                + "' and '"
                + ft.format(to)
                + "' and b.author='"
                + auth
                + "' group by book_id";

        List expected = new ArrayList();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        UserRequest ur = new UserRequest();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.summaryByAuthor(from, to, auth);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testAuthDateDateStringCatch() throws DAOException {
        Date from = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date to = cal.getTime();
        List<BookDetails> actual = booksDAO.summaryByAuthor(from, to, "fiction");
    }

    @Test
    public void testFetchbooks() throws DAOException {

        String var = "the";
        String hql = "from BookDetails where exist='true' and category='" + var
                + "' or name='" + var + "' or author='" + var + "'"
                + "or language like '%' || '" + var + "' || '%'";

        BookDetails bd = new BookDetails();
        BookDetails bd2 = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);
        expected.add(bd2);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.fetchbooks(var);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testFetchbooksCatch() throws DAOException {
        List<BookDetails> actual = booksDAO.fetchbooks("var");
    }

    @Test
    public void testFetch() throws DAOException {

        int bookid = 1;
        String hql = "from BookDetails where book_id = '" + bookid + "'";

        BookDetails bd = new BookDetails();
        List<BookDetails> expected = new ArrayList<BookDetails>();
        expected.add(bd);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<BookDetails> actual = booksDAO.fetchBookById(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused" })
    @Test(expected = DAOException.class)
    public void testFetchCatch() throws DAOException {
        List<BookDetails> actual = booksDAO.fetchBookById(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReqCount() throws DAOException {

        int bookid = 1;
        String sql = "select count(book_id) from UserRequest where book_id='"
                + bookid + "'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.reqCount(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testReqCountCatch() throws DAOException {
        List<UserRequest> actual = booksDAO.reqCount(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDlvrycnt() throws DAOException {

        int bookid = 1;
        String sql = "select count(book_id) from UserRequest where book_id='"
                + bookid
                + "' and (req_type='delivery' or req_type='return') and req_status<>'cancel'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.getDeliveryCount(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testDlvryCountCatch() throws DAOException {
        List<UserRequest> actual = booksDAO.getDeliveryCount(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRetcnt() throws DAOException {

        int bookid = 1;
        String sql = "select count(book_id) from UserRequest where book_id='"
                + bookid + "' and req_type='return' and req_status<>'cancel'";

        UserRequest ur = new UserRequest();
        List<UserRequest> expected = new ArrayList<UserRequest>();
        expected.add(ur);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.getReturnCount(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testRetcntCatch() throws DAOException {
        List<UserRequest> actual = booksDAO.getReturnCount(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCnclcnt() throws DAOException {

        int bookid = 1;
        String sql = "select count(book_id) from UserRequest where book_id='"
                + bookid + "' and req_type='delivery' and req_status='cancel'";

        List<UserRequest> expected = new ArrayList<UserRequest>();
        UserRequest ur = new UserRequest();
        expected.add(ur);
        UserRequest ur2 = new UserRequest();
        expected.add(ur2);

        booksDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<UserRequest> actual = booksDAO.getCancelCount(bookid);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testCnclcntCatch() throws DAOException {
        List<UserRequest> actual = booksDAO.getCancelCount(1);
    }

}
