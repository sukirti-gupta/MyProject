package com.impetus.bookstore.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.UserRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestDAO.
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequestDAO {

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
    private static final Logger LOG = Logger.getLogger(BooksDAO.class);

    /** The frmt. */
    private String frmt = "yyyy-MM-dd";

    /** The from query. */
    private String fromQuery = "from UserRequest where username ='";

    /**
     * Store book.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @param address
     *            the address
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean saveRequest(String username, int bookid, String address)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date date = new Date();

            UserRequest ur = new UserRequest();
            ur.setUsername(username);
            ur.setBookid(bookid);
            ur.setReqType("delivery");
            ur.setStartDate(date);
            ur.setReqStatus("pending");
            ur.setReqAddress(address);
            session.persist(ur);
            return true;
        } catch (Exception e) {
            LOG.error("Could not save delivery request for " + bookid
                    + " placed by " + username + " because "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not place request. Please try again.", e);
        }
    }

    /**
     * Gets the del.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the del
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserRequest> getDel(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery(fromQuery
                            + username
                            + "'"
                            + "and book_id='"
                            + bookid
                            + "'"
                            + "and ((req_type='delivery' and (req_status='pending' or req_status='close')) or (req_type='return' and req_status='pending'))");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return delivery request details for "
                    + username + e.getStackTrace());
            throw new DAOException(
                    "Could not place request. Please try again.", e);
        }
    }

    /**
     * Gets the bookshelf.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the bookshelf
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserRequest> getBookshelf(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(fromQuery + username + "'"
                    + "and book_id='" + bookid + "'"
                    + "and req_type='bookshelf'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return bookshelf details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not add to bookshelf. Please try again.", e);
        }
    }

    /**
     * Return book.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean returnBook(String username, int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();

            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat(frmt);

            Query query = session
                    .createQuery("update UserRequest set req_type='return', req_status='pending', end_date='"
                            + ft.format(date)
                            + "'"
                            + " where username ='"
                            + username
                            + "' and book_id='"
                            + bookid
                            + "' and req_type='delivery' and req_status='close'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not place return request for " + bookid + " by "
                    + username + e.getStackTrace());
            throw new DAOException(
                    "Could not place return request. Please try again.", e);
        }
    }

    /**
     * Cancel return.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean cancelReturn(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update UserRequest set req_status='cancel' "
                            + " where username ='" + username + "'"
                            + " and book_id='" + bookid + "'"
                            + " and req_type= 'return'"
                            + "and req_status = 'pending'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not cancel return request for " + bookid + " by "
                    + username + e.getStackTrace());
            throw new DAOException(
                    "Could not cancel return request. Please try again.", e);
        }
    }

    /**
     * Cancel delivery.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean cancelDelivery(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update UserRequest set req_status = 'cancel' "
                            + " where username ='"
                            + username
                            + "'"
                            + " and book_id='"
                            + bookid
                            + "'"
                            + " and req_type= 'delivery'"
                            + " and req_status = 'pending'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not cancel delivery request  for " + bookid
                    + " by " + username + e.getStackTrace());
            throw new DAOException(
                    "Could not cancel delivery request. Please try again.", e);
        }
    }

    /**
     * Adds the to bookshelf.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean addToBookshelf(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();

            Date date = new Date();

            UserRequest ur = new UserRequest();
            ur.setUsername(username);
            ur.setBookid(bookid);
            ur.setReqType("bookshelf");
            ur.setStartDate(date);
            ur.setReqStatus(null);
            session.persist(ur);
            return true;
        } catch (Exception e) {
            LOG.error("Could not add " + bookid + " to bookshelf for "
                    + username + e.getStackTrace());
            throw new DAOException(
                    "Could not add to bookshelf. Please try again.", e);
        }
    }

    /**
     * Removes the bookshelf.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean removeBookshelf(String username, int bookid)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("delete from UserRequest "
                    + " where username='" + username + "'" + " and book_id='"
                    + bookid + "'" + " and req_type= 'bookshelf'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not remove " + bookid + " from bookshelf for "
                    + username + e.getStackTrace());
            throw new DAOException(
                    "Could not remove from bookshelf. Please try again.", e);
        }

    }

    /**
     * Gets the req details.
     * 
     * @param username
     *            the username
     * @return the req details
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserRequest> getReqDetails(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(fromQuery + username + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return bookshelf details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve bookshelf details. Please try again.",
                    e);
        }

    }

    /**
     * Show bookshelf.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List bookshelf(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select book_id from UserRequest where username='"
                            + username + "' and req_type='bookshelf'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return bookshelf details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve bookshelf details. Please try again.",
                    e);
        }
    }

    /**
     * userReq1.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List booksHolding(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<UserRequest> history = session
                    .createQuery(
                            fromQuery
                                    + username
                                    + "'"
                                    + " and ((req_type= 'delivery' and req_status= 'close') or (req_type='return' and req_status='cancel'))")
                    .list();
            return history;
        } catch (Exception e) {
            LOG.error("Could not fetch history details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not fetch history details. Please try again.", e);
        }
    }

    /**
     * userReq2.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List booksToReturn(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<UserRequest> history = session
                    .createQuery(
                            fromQuery
                                    + username
                                    + "'"
                                    + "and req_type= 'return' and req_status= 'pending'")
                    .list();
            return history;
        } catch (Exception e) {
            LOG.error("Could not return history details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve history details. Please try again.", e);
        }
    }

    /**
     * userReq3.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List booksToDeliver(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<UserRequest> history = session
                    .createQuery(
                            fromQuery
                                    + username
                                    + "'"
                                    + "and req_type= 'delivery' and req_status= 'pending'")
                    .list();
            return history;
        } catch (Exception e) {
            LOG.error("Could not return history details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve history details. Please try again.", e);
        }
    }

    /**
     * userReq4.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List booksReturned(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<UserRequest> history = session
                    .createQuery(
                            fromQuery
                                    + username
                                    + "'"
                                    + " and req_type= 'return' and req_status= 'close'")
                    .list();
            return history;
        } catch (Exception e) {
            LOG.error("Could not return history details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve history details. Please try again.", e);
        }
    }

}
