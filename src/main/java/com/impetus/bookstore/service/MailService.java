package com.impetus.bookstore.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserRoles;

// TODO: Auto-generated Javadoc
/**
 * The Class MailService.
 */
@Service("mailService")
public class MailService {

    /** The mail sender. */
    @Autowired
    private MailSender mailSender;

    /** The simple mail message. */
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    /** The user dao. */
    @Autowired
    private UserDAO userDAO;

    /** The books dao. */
    @Autowired
    private BooksDAO booksDAO;

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(MailService.class);

    /**
     * Sets the simple mail message.
     * 
     * @param simpleMailMessage
     *            the new simple mail message
     */
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    /**
     * Sets the mail sender.
     * 
     * @param mailSender
     *            the new mail sender
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
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
     * @param booksDAO
     *            the new dao
     */
    public void setDAO(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    /**
     * Send mail.
     * 
     * @param session
     *            the session
     * @return true, if successful
     */
    @Transactional
    public boolean sendMail(HttpSession session) {
        try {
            String username = (String) session.getAttribute("usersession");
            String to = "";
            String dear = "";
            List<UserDetails> userdet = userDAO.getUserByUserName(username);
            for (UserDetails ud : userdet) {
                to = ud.getEid();
                dear = ud.getName();
            }

            int bookid = (Integer) session.getAttribute("bookid");
            String book = "";
            List<BookDetails> bookdet = booksDAO.requestedBook(bookid);
            for (BookDetails bd : bookdet) {
                book = bd.getName();
            }
            String req = (String) session.getAttribute("reqType");
            String subject = "Request for " + req + " placed.";
            String message = "Your " + req + " request for '" + book
                    + "' has been placed.";

            SimpleMailMessage email = new SimpleMailMessage(simpleMailMessage);
            email.setTo(to);
            email.setSubject(subject);
            email.setText(String.format(simpleMailMessage.getText(), dear,
                    message));
            LOG.debug("Sending mail to " + username + " for " + req
                    + " placed for " + book);
            mailSender.send(email);
            return true;
        } catch (DAOException e) {
            LOG.error("Problem encountered while sending mail."
                    + e.getExceptionCause());
            return false;
        }
    }

    /**
     * Send notification.
     * 
     * @param username
     *            the username
     * @param days
     *            the days
     * @return true, if successful
     */
    @Transactional
    public boolean sendNotification(String username, int days) {

        String to = "";
        String dear = "";
        try {
            List<UserDetails> userdet = userDAO.getUserByUserName(username);
            for (UserDetails ud : userdet) {
                to = ud.getEid();
                dear = ud.getName();
            }
            String subject = "Subscription plan updation.";
            String message = "This mail has been sent to bring to your notice that your subscription plan is about to expire in next "
                    + days
                    + " day(s)."
                    + "You will be needing to update a new plan to continue ordering books. Happy Reading. :)";

            SimpleMailMessage email = new SimpleMailMessage(simpleMailMessage);
            email.setTo(to);
            email.setSubject(subject);
            email.setText(String.format(simpleMailMessage.getText(), dear,
                    message));
            LOG.debug("Sending mail to " + username + " to notify");
            mailSender.send(email);
            return true;
        } catch (DAOException e) {
            LOG.error("Problem encountered while sending notifications."
                    + e.getExceptionCause());
            return false;
        }
    }

    /**
     * Send mail to admin.
     * 
     * @param file
     *            the file
     * @return true, if successful
     */
    @Transactional
    public boolean sendMailToAdmin(String file) {
        try {
            String dear = "";
            String to = "";
            List<UserRoles> admin = userDAO.getAllAdmin();
            for (int i = 0; i < admin.size(); i++) {
                List<UserDetails> userdetails = userDAO.getUserByUserName(admin
                        .get(i).getUsername());
                for (UserDetails ud : userdetails) {
                    to = ud.getEid();
                    dear = ud.getName();
                }
                String subject = "CSV file upload failed";
                String message = "This is to inform you that the csv file "
                        + file + " has failed to be uploaded to the database.";
                SimpleMailMessage email = new SimpleMailMessage(
                        simpleMailMessage);
                email.setTo(to);
                email.setSubject(subject);
                email.setText(String.format(simpleMailMessage.getText(), dear,
                        message));
                LOG.debug("Sending mail to " + admin.get(i).getUsername()
                        + " about file upload failure.");
                mailSender.send(email);
            }
            return true;
        } catch (DAOException e) {
            LOG.error("Problem encountered while sending mail to admin."
                    + e.getExceptionCause());
            return false;
        }
    }

}
