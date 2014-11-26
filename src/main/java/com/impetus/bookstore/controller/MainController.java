package com.impetus.bookstore.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.other.Scheduler;
import com.impetus.bookstore.other.Scheduler2;
import com.impetus.bookstore.service.BooksService;
import com.impetus.bookstore.service.RecommService;
import com.impetus.bookstore.service.Subservice;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
@RequestMapping("/")
@Controller
public class MainController {

    /** The books service. */
    @Autowired
    private BooksService booksService;

    /** The subservice. */
    @Autowired
    private Subservice subservice;

    /** The recomm service. */
    @Autowired
    private RecommService recommService;

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(MainController.class);

    /** The path. */
    @Value("${documents.root.path}")
    private String path;

    /** The cause. */
    private String cause = " Cause : ";

    /** The message. */
    private String message = "message";

    /** The redirect exhandler. */
    private String redirectExhandler = "redirect:exhandler";

    /** The could not serve. */
    private String couldNotServe = "Could not serve request. Please try again";

    /** The pop books retrieved. */
    private String popBooksRetrieved = "New books retrieved";

    /** The new books retrieved. */
    private String newBooksRetrieved = "Popular books retrieved";

    /** The error page. */
    private String errorPage = "Error";

    /** The error. */
    private String error = "error";

    /** The newbooks. */
    private String newbooks = "newbooks";

    /** The popular. */
    private String popular = "popular";

    /**
     * Home.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Timer time = new Timer();
        Timer time2 = new Timer();
        time.schedule(new Scheduler(booksService, path), calendar.getTime(),
                1000 * 60 * 60);

        time2.schedule(new Scheduler2(path), calendar.getTime(), 1000 * 60 * 60);

        try {
            List<BookDetails> newb = recommService.newbook();
            model.addAttribute(newbooks, newb);
            LOG.debug(newBooksRetrieved);

            List<BookDetails> pop = recommService.popular();
            model.addAttribute(popular, pop);
            LOG.debug(popBooksRetrieved);

            return "Home";
        } catch (NumberFormatException e) {
            LOG.error("Could not retrieve recommendations " + e.getCause());
            try {
                recommService.newPopular();

                List<BookDetails> newb = recommService.newbook();
                model.addAttribute(newbooks, newb);
                LOG.debug(newBooksRetrieved);

                List<BookDetails> pop = recommService.popular();
                model.addAttribute(popular, pop);
                LOG.debug(popBooksRetrieved);

                return "Home";
            } catch (ServiceException e1) {
                model.addAttribute(message, couldNotServe);
                return redirectExhandler;
            } catch (NumberFormatException e1) {
                model.addAttribute(message, couldNotServe);
                return redirectExhandler;
            }
        } catch (ServiceException e) {
            LOG.error("Could not load homepage - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }

    }

    /**
     * Home2.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/home")
    public String home2(Model model) {

        try {
            List<BookDetails> newb = recommService.newbook();
            model.addAttribute(newbooks, newb);
            LOG.debug(newBooksRetrieved);

            List<BookDetails> pop = recommService.popular();
            model.addAttribute(popular, pop);
            LOG.debug(popBooksRetrieved);
            return "Home";
        } catch (NumberFormatException e) {
            LOG.error("Could not retrieve recommendations " + e.getCause());
            model.addAttribute(message, couldNotServe);
            return redirectExhandler;
        } catch (ServiceException e) {
            LOG.error("Could not load homepage - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Login.
     * 
     * @return the string
     * @throws ServiceException
     *             the service exception
     */
    @RequestMapping(value = "/homeToLogin")
    public String login() throws ServiceException {
        LOG.debug("User logging in");
        return "Login";
    }

    /**
     * Register.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/homeToRegister")
    public String register(Model model) {
        try {
            List<Subscriptions> subs = subservice.getSub();
            model.addAttribute("subs", subs);
            model.addAttribute("ud", new UserDetails());
            LOG.debug("Registering new user");
            return "Register";
        } catch (ServiceException e) {
            LOG.error("Could not get subscription details - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Userhome.
     * 
     * @param status
     *            the status
     * @param model
     *            the model
     * @param session
     *            the session
     * @return the string
     */
    @RequestMapping(value = "/userhome")
    public String userhome(
            @RequestParam(value = "status", required = false) String status,
            Model model, HttpSession session) {
        try {
            model.addAttribute("ud", new UserDetails());

            List<String> auth = booksService.getAuthors();
            model.addAttribute("auth", auth);

            List<String> cat = booksService.getCategory();
            model.addAttribute("cat", cat);

            model.addAttribute("status", status);

            List<BookDetails> newb = recommService.newbook();
            model.addAttribute(newbooks, newb);
            LOG.debug(newBooksRetrieved);

            List<BookDetails> pop = recommService.popular();
            model.addAttribute(popular, pop);
            LOG.debug(popBooksRetrieved);

            String username = (String) session.getAttribute("usersession");
            List<BookDetails> recom = recommService.recombooks(username);
            model.addAttribute("recom", recom);
            LOG.debug("Recommended retrieved");

            BookDetails bd = new BookDetails();
            model.addAttribute("bd", bd);
            return "userHome";
        } catch (NumberFormatException e) {
            LOG.error("Could not retrieve recommendations " + e.getCause());
            model.addAttribute(message, couldNotServe);
            return redirectExhandler;
        } catch (ServiceException e) {
            LOG.error("Could not load homepage - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Exhandler.
     * 
     * @param message
     *            the message
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/exhandler", method = RequestMethod.GET)
    @ExceptionHandler({ ServiceException.class })
    public String exhandler(@ModelAttribute("message") String message,
            Model model) {
        model.addAttribute(error, message);
        return errorPage;
    }

    /**
     * Error404.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404(Model model) {
        model.addAttribute(error, "404. Page not found.");
        return errorPage;
    }

    /**
     * Err404.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/404", method = RequestMethod.POST)
    public String err404(Model model) {
        model.addAttribute(error, "404. Page not found.");
        return errorPage;
    }

    /**
     * Error500.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String error500(Model model) {
        model.addAttribute(error, "500. Bad Request.");
        return errorPage;
    }

    /**
     * Err500.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/500", method = RequestMethod.POST)
    public String err500(Model model) {
        model.addAttribute(error, "500. Bad Request.");
        return errorPage;
    }

}