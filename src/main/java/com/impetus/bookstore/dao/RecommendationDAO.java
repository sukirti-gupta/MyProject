package com.impetus.bookstore.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.NewAndPopular;

// TODO: Auto-generated Javadoc
/**
 * The Class RecommendationDAO.
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecommendationDAO {

    /** The session factory. */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Sets the session factory.
     * 
     * @param sessionFactory
     *            the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(RecommendationDAO.class);

    /**
     * Gets the newbooks.
     * 
     * @param date2
     *            the date2
     * @return the newbooks
     * @throws DAOException
     *             the DAO exception
     */
    public List getnewbooks(Date date2) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Query query = session
                    .createSQLQuery("select book_id from BookDetails  where date_added>'"
                            + ft.format(date2)
                            + "' and exist='true' "
                            + "order by date_added desc limit 10");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return new books " + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Gets the popular.
     * 
     * @return the popular
     * @throws DAOException
     *             the DAO exception
     */
    public List getpopular() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select b.book_id from UserRequest r,BookDetails b "
                            + "where r.book_id = b.book_id and b.exist='true' group by name order by count(b.book_id) desc limit 10");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return popular books " + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Savenewpop.
     * 
     * @param newb
     *            the newb
     * @param popb
     *            the popb
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean savenewpop(String newb, String popb) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date date = new Date();
            NewAndPopular np = new NewAndPopular();
            np.setDate(date);
            np.setNewbooks(newb);
            np.setPopular(popb);
            session.persist(np);
            return true;
        } catch (Exception e) {
            LOG.error("Could not save new and popular books "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not save new and popular books. Please try again.",
                    e);
        }
    }

    /**
     * Save recomm.
     * 
     * @param username
     *            the username
     * @param rec
     *            the rec
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */

    public boolean saveRecomm(String username, String rec) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update UserDetails set recommendations = '"
                            + rec + "' where username = '" + username + "'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not save recommendations " + e.getStackTrace());
            throw new DAOException(
                    "Could not save recommendations. Please try again.", e);
        }
    }

    /**
     * Favcategories.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List favcategories(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select b.category from UserRequest r, BookDetails b "
                            + "where r.book_id = b.book_id and r.username='"
                            + username + "' group by category");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return retrieve favourite categories "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not return retrieve favourite categories. Please try again.",
                    e);
        }
    }

    /**
     * Favlanguages.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List favlanguages(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select language from UserDetails where username='"
                            + username + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return retrieve favourite languages "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not return retrieve favourite languages. Please try again.",
                    e);
        }
    }

    /**
     * Favauthors.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */

    public List favauthors(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select b.author from UserRequest r, BookDetails b "
                            + "where r.book_id = b.book_id and r.username='"
                            + username + "' group by author");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return retrieve favourite authors "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not return retrieve favourite authors. Please try again.",
                    e);
        }
    }

    /**
     * New popular.
     * 
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<NewAndPopular> newPopular() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date today = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.roll(Calendar.DATE, -1);
            Date yesterday = cal.getTime();

            Query query = session.createQuery("from NewAndPopular where date='"
                    + ft.format(today) + "' or date='" + ft.format(yesterday)
                    + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return retrieve recommendations "
                    + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

}
