package com.impetus.bookstore.dao;

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
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionDAO.
 */
@Repository
@SuppressWarnings("unchecked")
public class SubscriptionDAO {

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
    private static final Logger LOG = Logger.getLogger(SubscriptionDAO.class);

    /**
     * Gets the sub.
     * 
     * @return the sub
     * @throws DAOException
     *             the DAO exception
     */
    public List<Subscriptions> getSub() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Subscriptions> subs = session.createQuery(
                    "from Subscriptions where validity='1'").list();
            return subs;
        } catch (Exception e) {
            LOG.error("Could not return subscriptions " + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

    /**
     * Gets the sub.
     * 
     * @param subscription
     *            the subscription
     * @return the sub
     * @throws DAOException
     *             the DAO exception
     */
    public List<Subscriptions> getSub(String subscription) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from Subscriptions where sub_name = '"
                            + subscription + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return subscription" + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

    public boolean updateSubscription(int subid, Subscriptions subs) throws DAOException{
        try{
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("update Subscriptions set sub_name='"
                    + subs.getSubName() + "', sub_amount='" + subs.getSubAmount()
                    + "', sub_duration='" + subs.getSubDuration()
                    + "', max_books='" + subs.getMaxBooks() + "', validity='"
                    + subs.getValidity() + "' where sub_id='"+subid+"'");
            query.executeUpdate();
            return true;
        } catch(Exception e) {
            LOG.error("Could not update subscription" + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

    public List<Subscriptions> getValidSub(String subscription)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from Subscriptions where sub_name = '"
                            + subscription + "' and validity='1'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return subscription" + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

    /**
     * Sub_details.
     * 
     * @param subid
     *            the subid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<Subscriptions> subDetails(int subid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Subscriptions> subs = session.createQuery(
                    "from Subscriptions where sub_id='" + subid + "'").list();
            return subs;
        } catch (Exception e) {
            LOG.error("Could not return subscription details for " + subid
                    + e.getStackTrace());
            throw new DAOException("Could not serve request. Please try again",
                    e);
        }
    }

    /**
     * User sub.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserSubs> userSub(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(
                    "from UserSubs where username='" + username
                    + "' and valid='1'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return subscription details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Gets the user sub.
     * 
     * @param username
     *            the username
     * @return the user sub
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserSubs> getUserSub(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from UserSubs where username='"
                    + username + "' and valid='1'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return subscription details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Gets the actv user.
     * 
     * @return the actv user
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserSubs> getActvUser() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from UserSubs where valid='1'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return users with active subscriptions "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not return users with active subscriptions. Please try again.",
                    e);
        }
    }

    /**
     * Adds the sub.
     * 
     * @param username
     *            the username
     * @param id
     *            the id
     * @param dur
     *            the dur
     * @param max
     *            the max
     * @param subscription
     *            the subscription
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean addSub(String username, int id, int dur, int max,
            String subscription) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date today = new Date();
            UserSubs us = new UserSubs();
            Calendar cal = Calendar.getInstance();
            us.setUsername(username);
            us.setSubid(id);
            us.setStartDate(today);
            cal.add(Calendar.MONTH, dur);
            Date date = cal.getTime();
            us.setEndDate(date);
            us.setBooksLeft(max);
            us.setValid(true);
            session.persist(us);
            return true;
        } catch (Exception e) {
            LOG.error("Could not return add subscription " + e.getStackTrace());
            throw new DAOException(
                    "Could not add subscription. Please try again.", e);
        }
    }

    /**
     * Invalidate sub.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean invalidateSub(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update UserSubs set valid='0' where (username='"
                            + username + "' and valid='1')");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not invalidate subscription " + e.getStackTrace());
            throw new DAOException(
                    "Could not update subscription. Please try again.", e);
        }
    }

    /**
     * Dec user sub.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean decUserSub(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query2 = session
                    .createQuery("update UserSubs set books_left= books_left-1"
                            + " where username='" + username + "'"
                            + "and valid='1'");
            query2.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not decrement books count for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Save or update.
     * 
     * @param sub
     *            the sub
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean saveSubscription(Subscriptions sub) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(sub);
            return true;
        } catch (Exception e) {
            LOG.error("Could not save or update subscription plan "
                    + e.getStackTrace());
            throw new DAOException("Could not upload subscription file", e);
        }
    }
}
