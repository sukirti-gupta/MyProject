package com.impetus.bookstore.dao;

import java.io.File;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.impetus.bookstore.model.BookDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchDao.
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class SearchDao {

    /** The session factory. */
    private SessionFactory sessionFactory;

    /** The session. */
    private Session session;

    /** The books dao. */
    private BooksDAO booksDAO;

    /**
     * Gets the session factory.
     * 
     * @return the session factory
     */
    private SessionFactory getSessionFactory() {
        return createConfiguration().buildSessionFactory();
    }

    /**
     * Creates the configuration.
     * 
     * @return the configuration
     */
    private Configuration createConfiguration() {
        return loadConfiguration();
    }

    /**
     * Load configuration.
     * 
     * @return the configuration
     */
    private Configuration loadConfiguration() {
        return new AnnotationConfiguration()
                .addAnnotatedClass(BookDetails.class)
                .configure(
                        new File(
                                "D:/SpringWorkspace/BookStacks/src/main/webapp/WEB-INF/hibernate-config.xml"));
    }

    /**
     * Search.
     * 
     * @param book
     *            the book
     * @return the list
     */
    public List<BookDetails> search(String book) {
        sessionFactory = getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        booksDAO = new BooksDAO();
        booksDAO.setSessionFactory(sessionFactory);
        Query query = session
                .createQuery("from BookDetails where (name like '%' || '"
                        + book + "' || '%' or author like '%' || '" + book
                        + "' || '%'" + " or category like '%' || '" + book
                        + "' || '%') and exist='true' order by dateAdded desc");
        return query.list();
    }
}
