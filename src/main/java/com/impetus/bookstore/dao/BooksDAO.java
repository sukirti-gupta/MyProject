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
import com.impetus.bookstore.model.BookDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class BooksDAO.
 */
@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BooksDAO {

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

    /** The select query. */
    private String selectQuery = "select count(book_id) from UserRequest where book_id='";

    /**
     * Find all.
     * 
     * @param book
     *            the book
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */

    public List<BookDetails> search(String book) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from BookDetails where (name like '%' || '"
                            + book
                            + "' || '%' or author like '%' || '"
                            + book
                            + "' || '%'"
                            + " or category like '%' || '"
                            + book
                            + "' || '%') and exist='true' order by dateAdded desc");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return books for " + book + " because "
                    + e.getStackTrace());
            throw new DAOException("Could not search books. Please try again.",
                    e);
        }
    }

    /**
     * Requested book.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<BookDetails> requestedBook(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from BookDetails where book_id='" + bookid
                            + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return book for book-id " + bookid
                    + " because " + e.getStackTrace());
            throw new DAOException("Could not search books. Please try again.",
                    e);
        }
    }

    /**
     * Dec book det.
     * 
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean decBookCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update BookDetails set avail= avail-1"
                            + " where book_id='" + bookid + "'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not decrement count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not place request. Please try again.", e);
        }
    }

    /**
     * Inc book det.
     * 
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean incBookCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query2 = session
                    .createQuery("update BookDetails set avail= avail+1"
                            + " where book_id='" + bookid + "'");
            query2.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not increment count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not place return request. Please try again.", e);
        }
    }

    /**
     * Find adm.
     * 
     * @param book
     *            the book
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<BookDetails> findAdm(String book) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from BookDetails where (name like '%' || '"
                            + book + "' || '%' or author like '%' || '" + book
                            + "' || '%'" + " or category like '%' || '" + book
                            + "' || '%')");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return details for book relating to " + book
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve books. Please try again.", e);
        }
    }

    /**
     * Gets the desc.
     * 
     * @param bookid
     *            the bookid
     * @return the desc
     * @throws DAOException
     *             the DAO exception
     */
    public List getDesc(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select b.description from BookDetails b where b.book_id='"
                            + bookid + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return description for book " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve description. Please try again.", e);
        }
    }

    /**
     * Adds the book.
     * 
     * @param name
     *            the name
     * @param description
     *            the description
     * @param bd2
     *            the bd2
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean addBook(String name, String description, BookDetails bd2)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Date date = new Date();
            BookDetails bd = new BookDetails();
            bd.setAuthor(bd2.getAuthor());
            bd.setAvail(bd2.getAvail());
            bd.setCategory(bd2.getCategory());
            bd.setDateAdded(date);
            bd.setDescription(description);
            bd.setExist(bd2.getExist());
            bd.setImage(bd2.getImage());
            bd.setLanguage(bd2.getLanguage());
            bd.setName(name);
            bd.setPublisher(bd2.getPublisher());
            session.persist(bd);
            return true;
        } catch (Exception e) {
            LOG.error("Could not add book " + e.getStackTrace());
            throw new DAOException("Could not add book. Please try again.", e);
        }
    }

    /**
     * Updt.
     * 
     * @param name
     *            the name
     * @param desc
     *            the desc
     * @param bd
     *            the bd
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean update(String name, String desc, BookDetails bd)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("update BookDetails set name='"
                    + name + "', author='" + bd.getAuthor() + "', publisher='"
                    + bd.getPublisher() + "', category='" + bd.getCategory()
                    + "'," + "language='" + bd.getLanguage()
                    + "', description='" + desc + "', avail='" + bd.getAvail()
                    + "', image='" + bd.getImage() + "', exist='"
                    + bd.getExist() + "'" + " where book_id='" + bd.getBookid()
                    + "'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not update book " + e.getStackTrace());
            throw new DAOException("Could not update book. Please try again.",
                    e);
        }
    }

    /**
     * Dltbook.
     * 
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws DAOException
     *             the DAO exception
     */
    public boolean deleteBook(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("update BookDetails set exist='false' where book_id = '"
                            + bookid + "'");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            LOG.error("Could not delete book " + e.getStackTrace());
            throw new DAOException("Could not delete book. Please try again.",
                    e);
        }
    }

    /**
     * Callproc.
     * 
     * @param str
     *            the str
     * @return the boolean
     * @throws DAOException
     *             the DAO exception
     */
    public Boolean uploadCSV(String str) throws DAOException {

        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("call crud_books(\"" + str
                    + "\")");
            query.executeUpdate();
            LOG.debug("Procedure for csv called successfully");
            return true;
        } catch (Exception e) {
            LOG.error("Could not call procedure -> " + e.getMessage());
            throw new DAOException("Could not search books", e);
        }
    }

    /**
     * Auth.
     * 
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List getAllAuthors() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select distinct author from BookDetails order by author");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return authors " + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve authors. Please try again.", e);
        }
    }

    /**
     * Cat.
     * 
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List getAllCategory() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery("select distinct category from BookDetails order by category");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return categories " + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve categories. Please try again.", e);
        }
    }

    /**
     * Duration.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List summaryByDuration(Date from, Date to) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            SimpleDateFormat ft = new SimpleDateFormat(frmt);
            Query query = session
                    .createSQLQuery("select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id and r.start_date between"
                            + "'"
                            + ft.format(from)
                            + "' and '"
                            + ft.format(to)
                            + "' group by book_id");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return summary for books requested from "
                    + from + " to " + to + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve summary. Please try again.", e);
        }
    }

    /**
     * Cat.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param cat
     *            the cat
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List summaryByCategory(Date from, Date to, String cat)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            SimpleDateFormat ft = new SimpleDateFormat(frmt);
            Query query = session
                    .createSQLQuery(
                            "select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id "
                                    + "and r.start_date between '"
                                    + ft.format(from)
                                    + "' and '"
                                    + ft.format(to)
                                    + "' "
                                    + "and b.category like :category group by book_id")
                    .setParameter("category", "%" + cat + "%");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return summary for books requested from "
                    + from + " to " + to + " for " + cat + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve summary. Please try again.", e);
        }
    }

    /**
     * Auth.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param auth
     *            the auth
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List summaryByAuthor(Date from, Date to, String auth)
            throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            SimpleDateFormat ft = new SimpleDateFormat(frmt);
            Query query = session
                    .createSQLQuery("select b.book_id from UserRequest r, BookDetails b where r.book_id = b.book_id "
                            + "and r.start_date between '"
                            + ft.format(from)
                            + "' and '"
                            + ft.format(to)
                            + "' and b.author='"
                            + auth + "' group by book_id");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return summary for books requested from "
                    + from + " to " + to + " by " + auth + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve summary. Please try again.", e);
        }
    }

    /**
     * Fetchbooks.
     * 
     * @param var
     *            the var
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<BookDetails> fetchbooks(String var) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from BookDetails where exist='true' and category='"
                            + var
                            + "' or name='"
                            + var
                            + "' or author='"
                            + var
                            + "'"
                            + "or language like '%' || '"
                            + var
                            + "' || '%'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return books requested for " + var
                    + e.getStackTrace());
            throw new DAOException("Could not retrieve books for " + var
                    + ". Please try again.", e);
        }
    }

    /**
     * Fetch.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List<BookDetails> fetchBookById(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createQuery("from BookDetails where book_id = '" + bookid
                            + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return book requested for id " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve book. Please try again.", e);
        }
    }

    /**
     * Req_count.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List reqCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(selectQuery + bookid + "'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return total request count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve total request count. Please try again.",
                    e);
        }
    }

    /**
     * Dlvrycnt.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List getDeliveryCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                    .createSQLQuery(selectQuery
                            + bookid
                            + "' and (req_type='delivery' or req_type='return') and req_status<>'cancel'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return delivery request count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve delivery request count. Please try again.",
                    e);
        }
    }

    /**
     * Retcnt.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List getReturnCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(selectQuery + bookid
                    + "' and req_type='return' and req_status<>'cancel'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return return request count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve return request count. Please try again.",
                    e);
        }
    }

    /**
     * Cnclcnt.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws DAOException
     *             the DAO exception
     */
    public List getCancelCount(int bookid) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(selectQuery + bookid
                    + "' and req_type='delivery' and req_status='cancel'");
            return query.list();
        } catch (Exception e) {
            LOG.error("Could not return cancel request count for " + bookid
                    + e.getStackTrace());
            throw new DAOException(
                    "Could not retrieve cancel request count. Please try again.",
                    e);
        }
    }

}
