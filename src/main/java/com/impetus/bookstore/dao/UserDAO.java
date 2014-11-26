package com.impetus.bookstore.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserRoles;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAO.
 */
@Repository
@SuppressWarnings({ "unchecked" })
public class UserDAO {

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
    private static final Logger LOG = Logger.getLogger(UserDAO.class);

    /**
     * Gets the all admin.
     * 
     * @return the all admin
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserRoles> getAllAdmin() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from UserRoles where role = 'ROLE_ADMIN'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not fetch all admins " + e.getStackTrace());
            throw new DAOException(
                    "Problem while fetching the admins from database.", e);
        }
    }

    /**
     * Email.
     * 
     * @param eid
     *            the eid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean email(String eid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from UserDetails where eid='"
                    + eid + "'");
            if (query.list().isEmpty()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LOG.error("Could not fetch details for " + eid + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Store data.
     * 
     * @param ud
     *            the ud
     * @param password
     *            the password
     * @return true, if successful
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws ConstraintViolationException
     *             the constraint violation exception
     * @throws DAOException
     *             the DAO exception
     */
    public boolean save(UserDetails ud, String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            ConstraintViolationException, DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            UserDetails ud2 = new UserDetails();
            ud2.setName(ud.getName());
            ud2.setUsername(ud.getUsername());
            ud2.setPassword(password);
            ud2.setEid(ud.getEid());
            ud2.setContact(ud.getContact());
            ud2.setAddress(ud.getAddress());
            ud2.setLanguage(ud.getLanguage());
            session.persist(ud2);
            return true;
        } catch (Exception e) {
            LOG.error("Could not save user details " + e.getStackTrace());
            throw new DAOException("Could not register. Please try again.", e);
        }
    }

    /**
     * Adds the user_role.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean addUserRole(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            UserRoles ur = new UserRoles();
            ur.setUsername(username);
            ur.setRole("ROLE_USER");
            session.persist(ur);
            return true;
        } catch (Exception e) {
            LOG.error("Could not add user role " + e.getStackTrace());
            throw new DAOException("Could not register. Please try again.", e);
        }
    }

    /**
     * Save admin.
     * 
     * @param ud
     *            the ud
     * @param password
     *            the password
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean saveAdmin(UserDetails ud, String password)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            UserDetails ud2 = new UserDetails();
            ud2.setContact(ud.getContact());
            ud2.setEid(ud.getEid());
            ud2.setName(ud.getName());
            ud2.setPassword(password);
            ud2.setUsername(ud.getUsername());
            session.persist(ud2);
            return true;
        } catch (Exception e) {
            LOG.error("Could not add admin " + e.getStackTrace());
            throw new DAOException(
                    "Could not register admin. Please try again.", e);
        }
    }

    /**
     * Adds the admin role.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean addAdminRole(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            UserRoles ur = new UserRoles();
            ur.setUsername(username);
            ur.setRole("ROLE_ADMIN");
            session.persist(ur);
            return true;
        } catch (Exception e) {
            LOG.error("Could not add admin role " + e.getStackTrace());
            throw new DAOException(
                    "Could not register admin. Please try again.", e);
        }
    }

    /**
     * All det.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserDetails> getUserByUserName(String username)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from UserDetails where username='" + username
                            + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not retrieve details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve details. Please try again.", e);
        }
    }

    /**
     * All det2.
     * 
     * @param eid
     *            the eid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserDetails> getUserByEmail(String eid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from UserDetails where eid='"
                    + eid + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not retrieve details for " + eid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * Active user.
     * 
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    @SuppressWarnings("rawtypes")
    public List activeUser() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date today = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Query query = session
                    .createQuery("from UserSubs where valid = '1' and end_date>='"
                            + ft.format(today) + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not retrieve active users " + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve active users. Please try again.", e);
        }
    }

    /**
     * Gets the user det.
     * 
     * @param username
     *            the username
     * @return the user det
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserDetails> getUserDet(String username) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from UserDetails where username='" + username
                            + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not retrieve details for " + username
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not serve request. Please try again.", e);
        }
    }

    /**
     * User req.
     * 
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<UserRequest> userReq() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from UserRequest where (req_type='delivery' or req_type='return') and (req_status='pending' or req_status='close')");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not retrieve user request details "
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve user request details. Please try again.",
                    e);
        }
    }

    /**
     * Updt req stat.
     * 
     * @param reqid
     *            the reqid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean updtReqStat(int reqid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update UserRequest set req_status='close' where req_id='"
                            + reqid + "'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not update request status " + e.getStackTrace());
            throw new DAOException(
                    "Could not update request status. Please try again.", e);
        }
    }

}
