package com.impetus.bookstore.DAO.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

import com.impetus.bookstore.dao.RecommendationDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.NewAndPopular;
import com.impetus.bookstore.model.UserRequest;

public class RecommendationDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Query query;
    private SQLQuery sqlquery;
    RecommendationDAO recommendationDAO = new RecommendationDAO();

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
        sqlquery = mock(SQLQuery.class);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testGetnewbooks() throws DAOException {

        Date date2 = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select book_id from BookDetails  where date_added>'"
                + ft.format(date2) + "' and exist='true' "
                + "order by date_added desc limit 10";

        List<BookDetails> expected = new ArrayList<BookDetails>();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        BookDetails bd2 = new BookDetails();
        expected.add(bd2);

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = recommendationDAO.getnewbooks(date2);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test(expected = DAOException.class)
    public void testGetnewbookscatch() throws DAOException {
        Date date2 = new Date();
        List<BookDetails> actual = recommendationDAO.getnewbooks(date2);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetpopular() throws DAOException {

        String sql = "select b.book_id from UserRequest r,BookDetails b "
                + "where r.book_id = b.book_id and b.exist='true' group by name order by count(b.book_id) desc limit 10";

        List expected = new ArrayList();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        UserRequest ur = new UserRequest();
        expected.add(ur);

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List<BookDetails> actual = recommendationDAO.getpopular();
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Test(expected = DAOException.class)
    public void testGetpopularcatch() throws DAOException {
        List<BookDetails> actual = recommendationDAO.getpopular();
    }

    @Test
    public void testSavenewpop() throws DAOException {

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Boolean actual = recommendationDAO.savenewpop("1,2,3", "4,5,6");
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testSavenewpopcatch() throws DAOException {
        Boolean actual = recommendationDAO.savenewpop("1,2,3", "4,5,6");
    }

    @Test
    public void testSaveRecomm() throws DAOException {

        String username = "abc";
        String rec = "1,2,3";
        String hql = "update UserDetails set recommendations = '"
                + rec + "' where username = '" + username + "'";
        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        Boolean actual = recommendationDAO.saveRecomm(username, rec);
        assertEquals(true, actual);
    }

    @SuppressWarnings("unused")
    @Test(expected = DAOException.class)
    public void testSaveRecommcatch() throws DAOException {
        Boolean actual = recommendationDAO.saveRecomm("abc", "1,2,3");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testFavcategories() throws DAOException {

        String username = "abc";
        String sql = "select b.category from UserRequest r, BookDetails b "
                + "where r.book_id = b.book_id and r.username='" + username
                + "' group by category";

        List expected = new ArrayList();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        UserRequest ur = new UserRequest();
        expected.add(ur);

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = recommendationDAO.favcategories(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testFavcategoriescatch() throws DAOException {
        List actual = recommendationDAO.favcategories("abc");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testFavlanguages() throws DAOException {

        String username = "abc";
        String sql = "select language from UserDetails where username='"
                + username + "'";
        List expected = new ArrayList();
        expected.add("english");

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = recommendationDAO.favlanguages(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testFavlanguagesCatch() throws DAOException {
        List actual = recommendationDAO.favlanguages("abc");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testFavauthors() throws DAOException {

        String username = "abc";
        String sql = "select b.author from UserRequest r, BookDetails b "
                + "where r.book_id = b.book_id and r.username='" + username
                + "' group by author";

        List expected = new ArrayList();
        BookDetails bd = new BookDetails();
        expected.add(bd);
        UserRequest ur = new UserRequest();
        expected.add(ur);

        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createSQLQuery(sql)).thenReturn(sqlquery);
        when(sqlquery.list()).thenReturn(expected);
        List actual = recommendationDAO.favauthors(username);
        assertEquals(expected, actual);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testFavauthorsCatch() throws DAOException {
        List actual = recommendationDAO.favauthors("abc");
    }@SuppressWarnings({ "unused", "rawtypes" })
    @Test(expected = DAOException.class)
    public void testNewPopularCatch() throws DAOException {
        List actual = recommendationDAO.newPopular();
    }
    
    
    @Test
    public void testNewPopular() throws DAOException{
        Date today = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        List<NewAndPopular> expected = new ArrayList<NewAndPopular>();
        NewAndPopular np = new NewAndPopular();
        expected.add(np);
        String hql = "from NewAndPopular where date='"
                + ft.format(today) + "' or date='" + ft.format(yesterday)
                + "'";
        
        recommendationDAO.setSessionFactory(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(expected);
        List<NewAndPopular> actual = recommendationDAO.newPopular();
        assertEquals(expected, actual);
    }
}
