package com.impetus.bookstore.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class BooksService.
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BooksService {

    /** The Books dao. */
    @Autowired
    private BooksDAO booksDAO;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /**
     * Sets the dao.
     * 
     * @param booksDAO
     *            the new dao
     */
    public void setDAO(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(BooksService.class);

    /** The Book. */
    private String book = "Book";

    /**
     * Find all.
     * 
     * @param book
     *            the book
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> search(String book) throws ServiceException {
        try {
            LOG.debug("Displaying all books related to " + book);
            return booksDAO.search(book);
        } catch (DAOException e) {
            LOG.error("Could not retrieve books for " + book);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Requested book.
     * 
     * @param bookid
     *            the bookid
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> requestedBook(int bookid) throws ServiceException {
        try {
            LOG.debug("Displaying details for " + bookid);
            return booksDAO.requestedBook(bookid);
        } catch (DAOException e) {
            LOG.error("Could not retrieve book for book-id " + bookid);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Find adm.
     * 
     * @param book
     *            the book
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<BookDetails> findAdm(String book) throws ServiceException {
        try {
            LOG.debug("Displaying books related to " + book
                    + " for updation/deletion");
            return booksDAO.findAdm(book);
        } catch (DAOException e) {
            LOG.error("Could not retrieve book related to " + book);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Dltbook.
     * 
     * @param bookid
     *            the bookid
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean deleteBook(int bookid) throws ServiceException {
        try {
            LOG.debug("Book id " + bookid + " deleted");
            booksDAO.deleteBook(bookid);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not delete book " + bookid);
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Updt.
     * 
     * @param bd
     *            the bd
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean update(BookDetails bd) throws ServiceException {
        try {
            int bookid = bd.getBookid();
            String name = bd.getName();
            name = name.replace("'", "");
            String description = bd.getDescription();
            description = description.replace("'", "");
            booksDAO.update(name, description, bd);
            LOG.debug(book + " id " + bookid + " updated");
            return true;
        } catch (DAOException e) {
            LOG.error("Could not update book");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Adds the.
     * 
     * @param bd
     *            the bd
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean add(BookDetails bd) throws ServiceException {
        try {
            String name = bd.getName();
            name = name.replaceAll("'", "");
            String description = bd.getDescription();
            description = description.replaceAll("'", "");
            booksDAO.addBook(name, description, bd);
            LOG.debug(book + name + " added to the database");
            return true;
        } catch (DAOException e) {
            LOG.error("Could not add book");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Uploadcsv.
     * 
     * @param csvfile
     *            the csvfile
     * @param path
     *            the path
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    public boolean uploadcsv(MultipartFile csvfile, String path)
            throws ServiceException {
        try {
            byte[] bytes = csvfile.getBytes();
            String name = csvfile.getOriginalFilename();

            String ext = name.substring(name.lastIndexOf('.'), name.length());
            if (ext.equals(".csv")) {
                File dir = new File(path + "\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.error("Could not upload csv");
            throw new ServiceException("Could not upload csv file", e);
        }
    }

    /**
     * Read lines.
     * 
     * @param filename
     *            the filename
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Transactional
    public String readCSV(String filename) throws IOException {
        LOG.debug("Reading " + filename);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {

            LOG.error("Could not read = " + filename + " => " + e);
            LOG.error(e.getMessage());
            return "";
        } finally {
            br.close();
        }
    }

    /**
     * Call dao.
     * 
     * @param str
     *            the str
     * @param file
     *            the file
     * @return true, if successful
     * @throws InstantiationException
     *             the instantiation exception
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws ClassNotFoundException
     *             the class not found exception
     * @throws SQLException
     *             the SQL exception
     */
    @Transactional
    public boolean uploadCsvToDatabse(String str, String file)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        try {
            booksDAO.uploadCSV(str);
            LOG.debug("Procedure called from DAO");
            return true;
        } catch (Exception e) {
            LOG.error("Could not call procedure from DAO => " + e.getMessage());
            mailService.sendMailToAdmin(file);
            return false;
        }
    }

    /**
     * Gets the authors.
     * 
     * @return the authors
     * @throws ServiceException
     *             the service exception
     */

    @Transactional
    public List<String> getAuthors() throws ServiceException {
        try {
            LOG.debug("Returning list of authors");
            return booksDAO.getAllAuthors();
        } catch (DAOException e) {
            LOG.error("Could not retrieve authors");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Gets the category.
     * 
     * @return the category
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<String> getCategory() throws ServiceException {
        try {
            LOG.debug("Returning list of categories");
            return booksDAO.getAllCategory();
        } catch (DAOException e) {
            LOG.error("Could not retrieve authors");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Summary.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param author
     *            the author
     * @param category
     *            the category
     * @return the map
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public Map<String, List> summary(Date from, Date to, String author,
            String category) throws ServiceException {
        try {
            String logInfo = "Book summary from " + from + " to " + to
                    + " for category " + category + " and author " + author
                    + "retrieved";
            int bookid = 0;
            List temp = new ArrayList();
            List ids = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();

            if ((author == null) && (category == null)) {
                books = summaryForDuration(from, to);
            }

            if (category != null) {
                books = summaryForCategory(from, to, category);
            }

            if (author != null) {
                temp = booksDAO.summaryByAuthor(from, to, author);
                LOG.debug(logInfo);
                for (int i = 0; i < temp.size(); i++) {
                    bookid = (Integer) temp.get(i);
                    temp2 = booksDAO.fetchBookById(bookid);
                    books.add(temp2.get(0));
                }
            }

            for (BookDetails bd : books) {
                temp = booksDAO.reqCount(bd.getBookid());
                ids.add(temp);
            }

            Map<String, List> m = new HashMap<String, List>();
            m.put("books", books);
            m.put("count", ids);
            LOG.debug("Returning summary for pdf report");
            return m;
        } catch (DAOException e) {
            LOG.error("Could not fetch summary");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Summary for duration.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    public List<BookDetails> summaryForDuration(Date from, Date to)
            throws ServiceException {
        try {
            int bookid = 0;
            List temp = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            temp = booksDAO.summaryByDuration(from, to);
            List<BookDetails> books = new ArrayList<BookDetails>();
            for (int i = 0; i < temp.size(); i++) {
                bookid = (Integer) temp.get(i);
                temp2 = booksDAO.fetchBookById(bookid);
                books.add(temp2.get(0));
            }
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve summary");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Summary for category.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param category
     *            the category
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    public List<BookDetails> summaryForCategory(Date from, Date to,
            String category) throws ServiceException {
        try {
            int bookid = 0;
            List temp = new ArrayList();
            List<BookDetails> temp2 = new ArrayList<BookDetails>();
            List<BookDetails> books = new ArrayList<BookDetails>();
            temp = booksDAO.summaryByCategory(from, to, category);
            for (int i = 0; i < temp.size(); i++) {
                bookid = (Integer) temp.get(i);
                temp2 = booksDAO.fetchBookById(bookid);
                books.add(temp2.get(0));
            }
            return books;
        } catch (DAOException e) {
            LOG.error("Could not retrieve summary");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Summary2.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the map
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public Map<String, List> summaryForRequestCount(Date from, Date to)
            throws ServiceException {
        try {
            int bookid = 0;
            List<BookDetails> books = new ArrayList<BookDetails>();
            List<BookDetails> temp1 = new ArrayList<BookDetails>();
            List temp = new ArrayList();
            List temp2 = new ArrayList();

            temp = booksDAO.summaryByDuration(from, to);
            LOG.debug("Book summary from " + from + " to " + to + " retrieved");
            for (int i = 0; i < temp.size(); i++) {
                bookid = (Integer) temp.get(i);
                temp1 = booksDAO.fetchBookById(bookid);
                books.add(temp1.get(0));
            }
            List dlvrycnt = new ArrayList();
            List retcnt = new ArrayList();
            List cnclcnt = new ArrayList();
            for (int i = 0; i < temp.size(); i++) {
                bookid = (Integer) temp.get(i);
                temp2 = booksDAO.getDeliveryCount(bookid);
                dlvrycnt.add(temp2.get(0));
                temp2 = booksDAO.getReturnCount(bookid);
                retcnt.add(temp2.get(0));
                temp2 = booksDAO.getCancelCount(bookid);
                cnclcnt.add(temp2.get(0));
            }
            LOG.debug("Request counts for each book retrieved");
            Map<String, List> m = new HashMap<String, List>();
            m.put("books", books);
            m.put("delivery", dlvrycnt);
            m.put("return", retcnt);
            m.put("cancel", cnclcnt);
            LOG.debug("Returning summary for pdf report");
            return m;
        } catch (DAOException e) {
            LOG.error("Could not retrieve summary");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

}
