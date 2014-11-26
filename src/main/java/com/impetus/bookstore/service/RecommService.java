package com.impetus.bookstore.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.dao.RecommendationDAO;
import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.NewAndPopular;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class RecommService.
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecommService {

    /** The recommendation dao. */
    @Autowired
    private RecommendationDAO recommendationDAO;

    /** The subscription dao. */
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    /** The books dao. */
    @Autowired
    private BooksDAO booksDAO;

    /** The user dao. */
    @Autowired
    private UserDAO userDAO;

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

    /**
     * Sets the dao.
     * 
     * @param recommendationDAO
     *            the new dao
     */
    public void setDAO(RecommendationDAO recommendationDAO) {
        this.recommendationDAO = recommendationDAO;
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(RecommService.class);

    /**
     * New popular.
     * 
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean newPopular() throws ServiceException {
        try {
            LOG.debug("Adding new and popular books for the day");

            int bookid = 0;
            String newb = "";
            String popb = "";
            Calendar cal = Calendar.getInstance();
            cal.get(Calendar.MONTH);
            cal.roll(Calendar.MONTH, -3);
            cal.get(Calendar.MONTH);
            Date date = cal.getTime();

            List newbooks = recommendationDAO.getnewbooks(date);
            Collections.shuffle(newbooks);
            LOG.debug("Retrieved new books");

            for (int i = 0; i < newbooks.size(); i++) {
                bookid = (Integer) newbooks.get(i);
                if (newbooks.size() - i > 1) {
                    newb = newb + bookid + ",";
                } else {
                    newb = newb + bookid;
                }
            }
            List popbooks = recommendationDAO.getpopular();
            Collections.shuffle(popbooks);
            LOG.debug("Retrieved popular books");

            for (int i = 0; i < popbooks.size(); i++) {
                bookid = (Integer) popbooks.get(i);
                if (popbooks.size() - i > 1) {
                    popb = popb + bookid + ",";
                } else {
                    popb = popb + bookid;
                }
            }
            recommendationDAO.savenewpop(newb, popb);
            LOG.debug("New and Popular books added");
            return true;
        } catch (DAOException e) {
            LOG.error("Could not build recommendations for new and popular books");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Recommendations.
     * 
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean recommendations() throws ServiceException {
        try {
            LOG.debug("Adding recommendations for the day");

            List<BookDetails> books = new ArrayList<BookDetails>();
            List<BookDetails> temp = new ArrayList<BookDetails>();
            List<UserSubs> actvUser = subscriptionDAO.getActvUser();

            LOG.debug("Users with actice subscriptions retrieved");
            for (UserSubs user : actvUser) {

                String username = user.getUsername();

                temp = favCategoryBooks(username);
                for (int i = 0; i < temp.size(); i++) {
                    books.add(temp.get(i));
                }
                temp = favLanguageBooks(username);
                for (int i = 0; i < temp.size(); i++) {
                    books.add(temp.get(i));
                }
                temp = favAuthorBooks(username);
                for (int i = 0; i < temp.size(); i++) {
                    books.add(temp.get(i));
                }
                Set<BookDetails> uniqueValues = new HashSet<BookDetails>(books);
                books.clear();
                for (BookDetails b : uniqueValues) {
                    books.add(b);
                }
                int id = 0;
                String str = "";
                Collections.shuffle(books);
                LOG.debug("Final recommendation = " + books);
                for (int i = 0; i < books.size(); i++) {
                    id = books.get(i).getBookid();
                    if (books.size() - i > 1) {
                        str = str + id + ",";
                    } else {
                        str = str + id;
                    }
                }
                recommendationDAO.saveRecomm(username, str);
            }
            LOG.debug("Recommendations updated");
            return true;
        } catch (DAOException e) {
            LOG.error("Recommendations could not be built");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Fav category books.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    public List<BookDetails> favCategoryBooks(String username)
            throws ServiceException {
        try {
            List<BookDetails> books = new ArrayList<BookDetails>();
            List temp = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();

            temp = recommendationDAO.favcategories(username);
            if (temp.isEmpty()) {
                temp = booksDAO.getAllCategory();
            }
            LOG.debug("Favourite categories for " + username + " are "
                    + temp.get(0));
            for (int i = 0; i < temp.size(); i++) {
                temp2 = booksDAO.fetchbooks(temp.get(i).toString());
                for (int j = 0; j < temp2.size(); j++) {
                    books.add(temp2.get(j));
                }
            }
            LOG.debug("Books for favourite categories = " + books);
            return books;
        } catch (DAOException e) {
            LOG.error("Could not build recommendations");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Fav language books.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    public List<BookDetails> favLanguageBooks(String username)
            throws ServiceException {
        try {
            List<BookDetails> books = new ArrayList<BookDetails>();
            List temp = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();

            temp = recommendationDAO.favlanguages(username);
            if (temp.isEmpty()) {
                temp.add("english");
            }
            LOG.debug("Favourite languages for " + username + " are "
                    + temp.get(0));
            for (int i = 0; i < temp.size(); i++) {
                temp2 = booksDAO.fetchbooks(temp.get(i).toString());
                if (temp2.isEmpty()) {
                    temp2 = booksDAO.fetchbooks("English");
                }
                for (int j = 0; j < temp2.size(); j++) {
                    books.add(temp2.get(j));
                }
            }
            LOG.debug("Books for favourite languages = " + books);
            return books;
        } catch (DAOException e) {
            LOG.error("Could not build recommendations");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Fav author books.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    public List<BookDetails> favAuthorBooks(String username)
            throws ServiceException {
        try {
            List<BookDetails> books = new ArrayList<BookDetails>();
            List temp = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();

            temp = recommendationDAO.favauthors(username);
            if (temp.isEmpty()) {
                temp = booksDAO.getAllAuthors();
            }
            LOG.debug("Favourite authors for " + username + " are "
                    + temp.get(0));
            for (int i = 0; i < temp.size(); i++) {
                temp2 = booksDAO.fetchbooks(temp.get(i).toString());
                for (int j = 0; j < temp2.size(); j++) {
                    books.add(temp2.get(j));
                }
            }
            LOG.debug("Books for favourite authors = " + books);
            return books;
        } catch (DAOException e) {
            LOG.error("Could not build recommendations");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Randomrecomm.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean randomrecomm(String username) throws ServiceException {

        int bookid = 0, rollmonth = -3;
        String rec = "";
        Calendar cal = Calendar.getInstance();
        cal.get(Calendar.MONTH);
        cal.roll(Calendar.MONTH, rollmonth);
        cal.get(Calendar.MONTH);
        Date date = cal.getTime();
        try {
            List newbooks = recommendationDAO.getnewbooks(date);
            Collections.shuffle(newbooks);
            LOG.debug("Retrieved new books");

            for (int i = 0; i < newbooks.size(); i++) {
                bookid = (Integer) newbooks.get(i);

                if (newbooks.size() - i > 1) {
                    rec = rec + bookid + ",";
                } else {
                    rec = rec + bookid;
                }
            }
            recommendationDAO.saveRecomm(username, rec);
            LOG.debug("Recommendations added");
            return true;
        } catch (DAOException e) {
            LOG.error("Could not build random recommendations");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Recombooks.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws NumberFormatException
     *             the number format exception
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> recombooks(String username)
            throws NumberFormatException, ServiceException {

        String bookList = "";
        List list2 = new ArrayList();
        List temp = new ArrayList();
        try {
            List<UserDetails> list1 = userDAO.getUserByUserName(username);
            for (UserDetails recom : list1) {
                bookList = recom.getRecommendations();
            }
            if (!bookList.isEmpty()) {
                String[] book = bookList.split(",");
                for (int i = 0; i < book.length; i++) {
                    int bookid = Integer.parseInt(book[i]);
                    temp = booksDAO.requestedBook(bookid);
                    list2.add(temp.get(0));
                }
                Collections.shuffle(list2);
                list2 = list2.subList(0, 6);
                LOG.debug("Returning recommended books");
                return list2;
            } else {
                return list2;
            }
        } catch (DAOException e) {
            LOG.error("Could not retrive recommended books");
            throw new ServiceException(e.getExceptionMsg(), e);
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving recommended books");
            throw new ServiceException("Something went wrong.", e);
        }
    }

    /**
     * Newbook.
     * 
     * @return the list
     * @throws NumberFormatException
     *             the number format exception
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> newbook() throws NumberFormatException,
            ServiceException {
        String bookList = "";
        List list2 = new ArrayList();
        List temp = new ArrayList();
        try {
            List<NewAndPopular> list1 = recommendationDAO.newPopular();
            for (NewAndPopular recom : list1) {
                bookList = recom.getNewbooks();
            }
            String[] book = bookList.split(",");
            for (int i = 0; i < book.length; i++) {
                int bookid = Integer.parseInt(book[i]);
                temp = booksDAO.requestedBook(bookid);
                list2.add(temp.get(0));
            }
            Collections.shuffle(list2);
            list2 = list2.subList(0, 6);
            LOG.debug("Returning new books");
            return list2;
        } catch (DAOException e) {
            LOG.error("Could not retrive new books");
            throw new ServiceException(e.getExceptionMsg(), e);
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving new books");
            throw new ServiceException("Something went wrong.", e);
        }
    }

    /**
     * Popular.
     * 
     * @return the list
     * @throws NumberFormatException
     *             the number format exception
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> popular() throws NumberFormatException,
            ServiceException {
        try {
            String bookList = "";
            List<NewAndPopular> list1 = recommendationDAO.newPopular();
            List list2 = new ArrayList();
            List temp = new ArrayList();

            for (NewAndPopular recom : list1) {
                bookList = recom.getPopular();
            }
            String[] book = bookList.split(",");
            for (int i = 0; i < book.length; i++) {
                int bookid = Integer.parseInt(book[i]);
                temp = booksDAO.requestedBook(bookid);
                list2.add(temp.get(0));
            }
            Collections.shuffle(list2);
            list2 = list2.subList(0, 6);
            LOG.debug("Returning popular books");
            return list2;
        } catch (DAOException e) {
            LOG.error("Could not retrive popular books");
            throw new ServiceException(e.getExceptionMsg(), e);
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving popular books");
            throw new ServiceException("Something went wrong.", e);
        }
    }
}
