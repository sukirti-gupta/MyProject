package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockHttpSession;

import com.impetus.bookstore.dao.BooksDAO;
import com.impetus.bookstore.dao.UserDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.service.MailService;

public class MailServiceTest {

    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;
    private UserDAO userDAO;
    private BooksDAO booksDAO;
    MailService mailService = new MailService();

    @Before
    public void setUp() {
        mailSender = mock(MailSender.class);
        simpleMailMessage = mock(SimpleMailMessage.class);
        userDAO = mock(UserDAO.class);
        booksDAO = mock(BooksDAO.class);
    }

    @Test
    public void testSendMail() throws DAOException, ServiceException {

        mailService.setSimpleMailMessage(simpleMailMessage);
        mailService.setMailSender(mailSender);
        mailService.setDAO(userDAO);
        mailService.setDAO(booksDAO);

        int bookid = 1;
        String username = "abc";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usersession", username);
        session.setAttribute("bookid", 1);
        String Message = "This mail has been sent to bring to your notice that your subscription plan is about to expire in next 30day(s)."
                + "You will be needing to update a new plan to continue ordering books. Happy Reading. :)";

        UserDetails ud = new UserDetails();
        BookDetails bd = new BookDetails();

        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        when(booksDAO.requestedBook(bookid)).thenReturn(Arrays.asList(bd));
        when(simpleMailMessage.getText()).thenReturn(Message);
        Boolean actual = mailService.sendMail(session);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByUserName(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(false, mailService.sendMail(session));
    }

    @Test
    public void testSendNotification() throws DAOException, ServiceException {

        mailService.setSimpleMailMessage(simpleMailMessage);
        mailService.setMailSender(mailSender);
        mailService.setDAO(userDAO);
        String username = "abc";
        int days = 30;

        String Message = "This mail has been sent to bring to your notice that your subscription plan is about to expire in next 30day(s)."
                + "You will be needing to update a new plan to continue ordering books. Happy Reading. :)";

        UserDetails ud = new UserDetails();
        when(userDAO.getUserByUserName(username)).thenReturn(Arrays.asList(ud));
        when(simpleMailMessage.getText()).thenReturn(Message);
        Boolean actual = mailService.sendNotification(username, days);
        assertEquals(true, actual);

        doThrow(new DAOException("error")).when(userDAO).getUserByUserName(username);
        ServiceException serviceException = new ServiceException("problem");
        serviceException.setExceptionMsg("error");
        assertEquals(false, mailService.sendNotification(username, days));
    }

}
