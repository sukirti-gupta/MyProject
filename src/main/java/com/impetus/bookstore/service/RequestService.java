package com.impetus.bookstore.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.dao.RequestDAO;
import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.UserRequest;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestService.
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequestService {

    /** The Books dao. */
    @Autowired
    private BooksDAO booksDAO;

    /** The request dao. */
    @Autowired
    private RequestDAO requestDAO;

    /** The Subscription dao. */
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    /**
     * Sets the dao.
     * 
     * @param booksDAO
     *            the new dao
     */
    public void setDAO(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    /**
     * Sets the dao.
     * 
     * @param requestDAO
     *            the new dao
     */
    public void setDAO(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
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
    private static final Logger LOG = Logger.getLogger(BooksService.class);

    /** The minuscount. */
    private String minuscount = "Book count decremented for ";

    /** The Book. */
    private String book = "Book";

    /**
     * Place req.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public String placeRequest(String username, int bookid)
            throws ServiceException {
        try {
            if (username != null) {
                LOG.debug(username + " already logged in");

                if (checkSubscription(username).equals("booksleft")) {
                    return checkBooks(username, bookid);
                } else {
                    LOG.debug("No more book requests can be placed by "
                            + username);
                    return checkSubscription(username);
                }
            } else {
                LOG.debug(username + " not yet logged in");
                return "log in";
            }
        } catch (ServiceException e) {
            LOG.error(username + " could not place request");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Check subscription.
     * 
     * @param username
     *            the username
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    public String checkSubscription(String username) throws ServiceException {
        try {
            Date today = new Date();
            Date endDate = new Date();
            Calendar cal = Calendar.getInstance();
            int booksLeft = 0;
            List<UserSubs> userSub = subscriptionDAO.getUserSub(username);
            for (UserSubs det : userSub) {
                endDate = det.getEndDate();
                cal.setTime(endDate);
                cal.roll(Calendar.DATE, -5);
                endDate = cal.getTime();
            }
            if (endDate.compareTo(today) > 0) {
                LOG.debug(username + " has valid subscription");
                for (UserSubs det : userSub) {
                    booksLeft = det.getBooksLeft();
                }
                if (booksLeft > 0) {
                    LOG.debug(username + " has books left to be ordered");
                    return "booksleft";
                } else {
                    LOG.debug("No more book requests can be placed by "
                            + username);
                    return "nobooksleft";
                }
            } else {
                LOG.debug(username + " needs to update subscription plan");
                return "subinvalid";
            }
        } catch (DAOException e) {
            LOG.error(username + " could not place request");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Check books.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    public String checkBooks(String username, int bookid)
            throws ServiceException {
        try {
            int avail = 0;
            List<UserRequest> books = requestDAO.getDel(username, bookid);
            if (books.isEmpty()) {
                LOG.debug("Delivery request not yet placed by " + username
                        + " for " + bookid);

                List<BookDetails> booksdet = booksDAO.requestedBook(bookid);
                for (BookDetails bd : booksdet) {
                    avail = bd.getAvail();
                }
                if (avail > 0) {
                    LOG.debug(book + bookid + " available");
                    return "confirm";
                } else {
                    LOG.debug(book + bookid + " not available");
                    return "notavailable";
                }
            } else {
                LOG.debug("Delivery request for " + bookid
                        + " has already been placed by " + username);
                return "bookexist";
            }
        } catch (DAOException e) {
            LOG.error(username + " could not place request");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

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
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean saveRequest(String username, int bookid, String address)
            throws ServiceException {
        try {
            requestDAO.saveRequest(username, bookid, address);
            LOG.debug("Delivery request for " + bookid + " placed by "
                    + username);
            int avail = 0, booksLeft = 0;
            List<BookDetails> bookdet = booksDAO.requestedBook(bookid);
            for (BookDetails bd : bookdet) {
                avail = bd.getAvail();
            }
            if (avail > 0) {
                booksDAO.decBookCount(bookid);
                LOG.debug(minuscount + bookid);
            }
            List<UserSubs> userSub = subscriptionDAO.getUserSub(username);
            for (UserSubs det : userSub) {
                booksLeft = det.getBooksLeft();
            }
            if (booksLeft > 0) {
                subscriptionDAO.decUserSub(username);
                LOG.debug(minuscount + username);
            }
            return true;
        } catch (DAOException e) {
            LOG.error("Could not save request");
            throw new ServiceException(e.getExceptionMsg(), e);
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
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean returnBook(String username, int bookid)
            throws ServiceException {
        try {
            requestDAO.returnBook(username, bookid);
            LOG.debug("Return request placed for " + bookid + " by " + username);
            booksDAO.incBookCount(bookid);
            LOG.debug(book + " count incremented for " + bookid);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not place return request");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Cancel return.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public String cancelReturn(String username, int bookid)
            throws ServiceException {
        try {
            int avail = 0;
            Date today = new Date();
            Date endDate = new Date();
            Calendar cal = Calendar.getInstance();

            List<UserSubs> userSub = subscriptionDAO.getUserSub(username);
            for (UserSubs det : userSub) {
                endDate = det.getEndDate();
                cal.add(Calendar.DATE, -5);
                endDate = cal.getTime();
            }
            if (today.compareTo(endDate) > 0) {
                requestDAO.cancelReturn(username, bookid);
                LOG.debug("Return request cancelled for " + bookid + " by "
                        + username);

                List<BookDetails> bookdet = booksDAO.requestedBook(bookid);
                for (BookDetails bd : bookdet) {
                    avail = bd.getAvail();
                }
                if (avail > 0) {
                    booksDAO.decBookCount(bookid);
                    LOG.debug(minuscount + bookid);
                }
                return "Return request cancelled !";
            } else {
                return "Your subscription plan needs to be updated for this request.";
            }
        } catch (DAOException e) {
            LOG.error("Could not cancel return request");
            throw new ServiceException(e.getExceptionMsg(), e);
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
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean cancelDelivery(String username, int bookid)
            throws ServiceException {
        try {
            requestDAO.cancelDelivery(username, bookid);
            LOG.debug("Delivery request cancelled for " + bookid + " by "
                    + username);
            booksDAO.incBookCount(bookid);
            LOG.debug(minuscount + bookid);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not cancel delivery request");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Adds the to bookshelf.
     * 
     * @param username
     *            the username
     * @param bookid
     *            the bookid
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public String addToBookshelf(String username, int bookid)
            throws ServiceException {
        try {
            if (username != null) {
                List<UserRequest> userreq = requestDAO.getBookshelf(username,
                        bookid);
                if (userreq.isEmpty()) {
                    requestDAO.addToBookshelf(username, bookid);
                    LOG.debug(bookid + " added to bookshelf by " + username);
                    return "done";
                } else {
                    return "bookexist";
                }
            } else {
                LOG.debug(username + " not logged in yet");
                return "log in";
            }
        } catch (DAOException e) {
            LOG.error("Could not add to the bookshelf");
            throw new ServiceException(e.getExceptionMsg(), e);
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
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean removeBookshelf(String username, int bookid)
            throws ServiceException {
        try {
            requestDAO.removeBookshelf(username, bookid);
            LOG.debug(book + bookid + " removed from bookshelf by " + username);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not remove from bookshelf");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Gets the req details.
     * 
     * @param username
     *            the username
     * @return the req details
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<UserRequest> getReqDetails(String username)
            throws ServiceException {
        try {
            LOG.debug("Returning request details for " + username);
            return requestDAO.getReqDetails(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve bookshelf details");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Show bookshelf.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */

    @Transactional
    public List showBookshelf(String username) throws ServiceException {
        try {
            LOG.debug("Returning bookshelf books for " + username);
            List<BookDetails> books = new ArrayList<BookDetails>();
            List temp = requestDAO.bookshelf(username);
            for (int i = 0; i < temp.size(); i++) {
                List<BookDetails> temp2 = booksDAO.fetchBookById((Integer) temp
                        .get(i));
                books.add(temp2.get(0));
            }
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve bookshelf details");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * userReq1.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List booksHolding(String username) throws ServiceException {
        try {
            LOG.debug("Returning request details for currently holding books for "
                    + username);
            return requestDAO.booksHolding(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve details for currently holding books");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Show current.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List showCurrent(String username) throws ServiceException {
        try {
            List<UserRequest> temp = new ArrayList<UserRequest>();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();

            temp = requestDAO.booksHolding(username);
            for (UserRequest req : temp) {
                temp2 = booksDAO.fetchBookById(req.getBookid());
                books.add(temp2.get(0));
            }
            LOG.debug("Returning currently holding books");
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve currently holding books");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * userReq2.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List booksToReturn(String username) throws ServiceException {
        try {
            LOG.debug("Returning request details for books to be returned for "
                    + username);
            return requestDAO.booksToReturn(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve details for books to be returned");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * To return.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List toReturn(String username) throws ServiceException {
        try {
            List<UserRequest> temp = new ArrayList<UserRequest>();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();

            temp = requestDAO.booksToReturn(username);
            for (UserRequest req : temp) {
                temp2 = booksDAO.fetchBookById(req.getBookid());
                books.add(temp2.get(0));
            }
            LOG.debug("Returning books to be returned");
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve books to be returned");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * userReq3.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List booksToDeliver(String username) throws ServiceException {
        try {
            LOG.debug("Returning request details for books to be delivered for "
                    + username);
            return requestDAO.booksToDeliver(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve details for books to be delivered");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * To deliver.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List toDeliver(String username) throws ServiceException {
        try {
            List<UserRequest> temp = new ArrayList<UserRequest>();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();

            temp = requestDAO.booksToDeliver(username);
            for (UserRequest req : temp) {
                temp2 = booksDAO.fetchBookById(req.getBookid());
                books.add(temp2.get(0));
            }
            LOG.debug("Returning books to be delivered");
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve books to be delivered");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * userReq4.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List booksReturned(String username) throws ServiceException {
        try {
            LOG.debug("Returning request details for books already returned for "
                    + username);
            return requestDAO.booksReturned(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve details for books already returned");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Returned.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List returned(String username) throws ServiceException {
        try {
            List<UserRequest> temp = new ArrayList<UserRequest>();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();

            temp = requestDAO.booksReturned(username);
            for (UserRequest req : temp) {
                temp2 = booksDAO.fetchBookById(req.getBookid());
                books.add(temp2.get(0));
            }
            LOG.debug("Returning books already read");
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve books already returned");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

}
