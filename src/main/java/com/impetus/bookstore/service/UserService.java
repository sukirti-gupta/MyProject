package com.impetus.bookstore.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class UserService.
 */
@Service
public class UserService {

    /** The User dao. */
    @Autowired
    private UserDAO userDAO;

    /** The Subscription dao. */
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    /**
     * Sets the dao.
     * 
     * @param userDAO
     *            the new dao
     */
    public void setDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Sets the dao.
     * 
     * @param subscriptionDAO
     *            the new dao
     */
    public void setDAO(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(UserService.class);

    /**
     * Email.
     * 
     * @param eid
     *            the eid
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean email(String eid) throws ServiceException {
        try {
            List<UserDetails> userdet = userDAO.getUserByEmail(eid);
            if (userdet.isEmpty()) {
                LOG.debug("Email id " + eid + " not yet registered");
                return true;
            } else {
                LOG.debug("Email id " + eid + " already registered");
                return false;
            }
        } catch (DAOException e) {
            LOG.error("Could not retrieve details for " + eid);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Store data.
     * 
     * @param ud
     *            the ud
     * @param password
     *            the password
     * @param subscription
     *            the subscription
     * @return true, if successful
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean save(UserDetails ud, String password, String subscription)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            ServiceException {
        try {
            int id = 0, dur = 0, max = 0;

            userDAO.save(ud, password);
            LOG.debug("User data stored for " + ud.getUsername());
            userDAO.addUserRole(ud.getUsername());
            LOG.debug("User role stored for " + ud.getUsername());
            List<Subscriptions> subs = subscriptionDAO.getSub(subscription);
            for (Subscriptions sub : subs) {
                id = sub.getSubid();
                dur = sub.getSubDuration();
                max = sub.getMaxBooks();
            }
            subscriptionDAO.invalidateSub(ud.getUsername());
            subscriptionDAO
                    .addSub(ud.getUsername(), id, dur, max, subscription);
            LOG.debug("User subscription stored for " + ud.getUsername());
            LOG.debug(ud.getUsername() + " registered successfully");
            return true;
        } catch (DAOException e) {
            LOG.error("Could not save user details");
            throw new ServiceException(e.getExceptionMsg(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("Could not save user details");
            throw new ServiceException(
                    "We encountered an error. Please try again.", e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Could not save user details");
            throw new ServiceException(
                    "We encountered an error. Please try again", e);
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
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean saveAdmin(UserDetails ud, String password)
            throws ServiceException {
        try {
            userDAO.saveAdmin(ud, password);
            LOG.debug("Admin data stored for " + ud.getUsername());
            userDAO.addAdminRole(ud.getUsername());
            LOG.debug("Admin role stored for " + ud.getUsername());
            return true;
        } catch (DAOException e) {
            LOG.error("Could not save admin details");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * User.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<UserDetails> user(String username) throws ServiceException {
        try {
            LOG.debug("Retrieving details for " + username);
            return userDAO.getUserByUserName(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve user details for " + username);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Address.
     * 
     * @param username
     *            the username
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public String address(String username) throws ServiceException {
        try {
            String address = "";
            List<UserDetails> userdet = userDAO.getUserByUserName(username);
            for (UserDetails user : userdet) {
                address = user.getAddress();
            }
            LOG.debug("Retrieving address for placing delivery request");
            return address;
        } catch (DAOException e) {
            LOG.error("Could not retrieve address for " + username);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Active user.
     * 
     * @return the map
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, List> activeUser() throws ServiceException {
        try {
            List temp = new ArrayList();
            List userdet = new ArrayList();
            List<UserSubs> usersub = userDAO.activeUser();
            for (UserSubs uname : usersub) {
                String username = uname.getUsername();
                temp.add(username);
            }
            for (int i = 0; i < temp.size(); i++) {
                List temp2 = userDAO.getUserDet(temp.get(i).toString());
                userdet.add(temp2.get(0));
            }
            Map<String, List> m = new HashMap<String, List>();
            m.put("usersub", usersub);
            m.put("userdet", userdet);
            LOG.debug("Returning all details for users with active subscription");
            return m;
        } catch (DAOException e) {
            LOG.error("Could not retrieve active users");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * User req.
     * 
     * @return the map
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, List> userReq() throws ServiceException {
        try {
            List temp = new ArrayList();
            List users = new ArrayList();
            List<UserRequest> userReq = userDAO.userReq();
            for (UserRequest user : userReq) {
                String name = user.getUsername();
                temp = userDAO.getUserByUserName(name);
                users.add(temp.get(0));
            }
            Map<String, List> m = new HashMap<String, List>();
            m.put("users", users);
            m.put("userReq", userReq);
            LOG.debug("Returning all details for user requests and their status");
            return m;
        } catch (DAOException e) {
            LOG.error("Could not retrieve user request details");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Chng req stat.
     * 
     * @param reqid
     *            the reqid
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean chngReqStat(int reqid) throws ServiceException {
        try {
            userDAO.updtReqStat(reqid);
            LOG.debug("Request closed for request id " + reqid);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not change request status ");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

}
