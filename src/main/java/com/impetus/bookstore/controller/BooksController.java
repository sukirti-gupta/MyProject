package com.impetus.bookstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.service.BooksService;
import com.impetus.bookstore.service.MailService;
import com.impetus.bookstore.service.RequestService;
import com.impetus.bookstore.service.UserService;

// TODO: Auto-generated Javadoc
/**
 * The Class BooksController.
 */
@Controller
@RequestMapping("/")
public class BooksController {

    /** The Books service. */
    @Autowired
    private BooksService booksService;

    /** The Request service. */
    @Autowired
    private RequestService requestService;

    /** The User service. */
    @Autowired
    private UserService userService;

    /** The Mail service. */
    @Autowired
    private MailService mailService;

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(BooksController.class);

    /** The redirect exhandler. */
    private String redirectExhandler = "redirect:exhandler";

    /** The no books to display. */
    private String noBooksToDisplay = "No books to display";

    /** The message. */
    private String message = "message";

    /** The req type. */
    private String reqType = "reqType";

    /** The placed. */
    private String placed = "placed";

    /** The usersession. */
    private String usersession = "usersession";

    /** The mail sent to. */
    private String mailSentTo = "Mail sent to ";

    /** The cause. */
    private String cause = " Cause : ";

    /** The failed upload. */
    private String failedUpload = "Failed to upload ";

    /** The status. */
    private String status = "status";

    /** The book model. */
    private String bookModel = "books";

    /**
     * Findbooks.
     * 
     * @param book
     *            the book
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findbooks(@RequestParam("book") String book, Model model) {
        try {
            List<BookDetails> books = booksService.findAdm(book);
            if (books.isEmpty()) {
                model.addAttribute("error", "No books found.");
                LOG.debug("No books found for " + book);
            } else {
                model.addAttribute(bookModel, books);
                LOG.debug("Displaying books for " + book);
            }
            return "UpdDelBook";
        } catch (ServiceException e) {
            LOG.error("Could not find books " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Dltbook.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/dltbook", method = RequestMethod.GET)
    public @ResponseBody
    String deleteBook(@RequestParam("bookid") int bookid, Model model) {
        try {
            booksService.deleteBook(bookid);
            LOG.debug("Book_id " + bookid + " deleted");
            return "deleted";
        } catch (ServiceException e) {
            LOG.error("Could not delete book - " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Updtbook.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/toupdate", method = RequestMethod.GET)
    public String updtbook(@RequestParam("bookid") int bookid, Model model) {
        try {
            List<BookDetails> books = booksService.requestedBook(bookid);
            model.addAttribute(bookModel, books);
            model.addAttribute("bd", new BookDetails());
            LOG.debug("Admin wants to update " + bookid);
            return "UpdtBook";
        } catch (ServiceException e) {
            LOG.error("Could not load update page");
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Updt.
     * 
     * @param bd
     *            the bd
     * @param bindingResult
     *            the binding result
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("bd") BookDetails bd,
            BindingResult bindingResult, Model model) {
        try {
            int bookid = bd.getBookid();
            booksService.update(bd);
            LOG.debug("Book id " + bookid + " updated");
            model.addAttribute(status, "The book has been updated");
            return "redirect:userhome";
        } catch (ServiceException e) {
            LOG.error("Could not update book - " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Adds the.
     * 
     * @param bd
     *            the bd
     * @param bindingResult
     *            the binding result
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("bd") BookDetails bd,
            BindingResult bindingResult, Model model) {
        try {
            String name = bd.getName();
            booksService.add(bd);
            LOG.debug("Book " + name + " added to the database");
            model.addAttribute(status, "The book has been added");
            return "redirect:userhome";
        } catch (ServiceException e) {
            LOG.error("Could not add book - " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /** The path. */
    @Value("${documents.root.path}")
    private String path;

    /**
     * Uploadcsv.
     * 
     * @param file
     *            the file
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/uploadcsv", method = RequestMethod.POST)
    public String uploadcsv(@RequestParam("file") MultipartFile file,
            Model model) {

        String fileName = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                if (booksService.uploadcsv(file, path)) {
                    model.addAttribute(status, "Successfully uploaded "
                            + fileName);
                } else {
                    model.addAttribute(status, "Failed to upload " + fileName);
                }
            } catch (Exception e) {
                model.addAttribute(status, failedUpload + fileName);
                LOG.error(failedUpload + fileName + "=> " + e.getMessage());
            }
        } else {
            model.addAttribute(status, failedUpload + fileName
                    + " because the file was empty.");
            LOG.error(failedUpload + fileName + " because it was empty");
        }
        return "redirect:userhome";
    }

    /**
     * Generate pdf.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param author
     *            the author
     * @param category
     *            the category
     * @param model
     *            the model
     * @param map
     *            the map
     * @return the model and view
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/generatepdf", method = RequestMethod.POST)
    ModelAndView generatePdf(
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to", required = false) Date to,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) String category,
            Model model, Map<String, Object> map) {
        try {
            LOG.debug("Generating PDF report");
            Map<String, List> m = booksService.summary(from, to, author,
                    category);
            List<BookDetails> books = m.get(bookModel);
            List count = m.get("count");
            model.addAttribute("count", count);
            model.addAttribute("from", from);
            model.addAttribute("to", to);
            return new ModelAndView("pdfView", "listBooks", books);
        } catch (Exception e) {
            LOG.error("Could not generate report - " + e.getMessage() + cause
                    + e.getCause());
            return new ModelAndView("exhandler", message,
                    "Could not generate report");
        }
    }

    /**
     * Generate pdf2.
     * 
     * @param from
     *            the from
     * @param to
     *            the to
     * @param model
     *            the model
     * @param map
     *            the map
     * @return the model and view
     * @throws ServiceException
     *             the service exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/pdfreport", method = RequestMethod.POST)
    ModelAndView generatePdf2(@RequestParam("from") Date from,
            @RequestParam("to") Date to, Model model, Map<String, Object> map)
            throws ServiceException {
        try {
            LOG.debug("Generating PDF Report");
            Map<String, List> m = booksService.summaryForRequestCount(from, to);
            List<BookDetails> books = m.get(bookModel);
            List delivery = m.get("delivery");
            List returnc = m.get("return");
            List cancel = m.get("cancel");
            model.addAttribute("delivery", delivery);
            model.addAttribute("return", returnc);
            model.addAttribute("cancel", cancel);
            return new ModelAndView("pdfView2", "listBooks", books);
        } catch (ServiceException e) {
            LOG.error("Could not generate report - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            return new ModelAndView("exhandler", message, e.getExceptionMsg());
        }

    }

    /**
     * Book list.
     * 
     * @param book
     *            the book
     * @param model
     *            the model
     * @param session
     *            the session
     * @return the string
     */
    @RequestMapping(value = "/displayBooks", method = RequestMethod.GET)
    public String search(@RequestParam("book") String book, Model model,
            HttpSession session) {

        session.getAttribute(usersession);
        model.addAttribute("category", book);
        session.setAttribute("book", book);
        try {
            List<BookDetails> books = booksService.search(book);
            if (books.isEmpty()) {
                model.addAttribute("error", "No books found.");
                LOG.debug("Books not found for " + book);
            } else {
                model.addAttribute(bookModel, books);
                LOG.debug("Displaying books for " + book);
            }
            return "DisplayBooks";
        } catch (ServiceException e) {
            LOG.error("Could not display books - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Book r.
     * 
     * @param book
     *            the book
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the list
     */
    @RequestMapping(value = "/book/get", method = RequestMethod.POST)
    public @ResponseBody
    List<BookDetails> bookR(@RequestParam("book") String book, Model model,
            HttpSession session, HttpServletRequest request) {

        session.getAttribute(usersession);
        model.addAttribute("category", book);
        session.setAttribute("book", book);
        List<BookDetails> books = new ArrayList<BookDetails>();
        try {
            books = booksService.search(book);
            if (books.isEmpty()) {
                model.addAttribute("error", "No books found.");
                LOG.debug("Books not found for " + book);
            } else {
                model.addAttribute(bookModel, books);
                LOG.debug("Displaying books for " + book);
            }
            return books;
        } catch (ServiceException e) {
            LOG.error("Could not display book - " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return books;
        }
    }

    /**
     * Requested book.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/requestedBook", method = RequestMethod.GET)
    public String requestedBook(@RequestParam("bookid") int bookid,
            Model model, HttpServletRequest request) {
        try {
            List<BookDetails> books = booksService.requestedBook(bookid);
            model.addAttribute(bookModel, books);
            LOG.debug("Displaying book id " + bookid);
            return "RequestedBook";
        } catch (ServiceException e) {
            LOG.error("Could not diasplay book - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Place request.
     * 
     * @param bookid
     *            the bookid
     * @param session
     *            the session
     * @param request
     *            the request
     * @param response
     *            the response
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/placeRequest", method = RequestMethod.GET)
    public @ResponseBody
    String placeRequest(@RequestParam("bookid") int bookid,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        try {
            LOG.debug("Delivery request for " + bookid);
            String username = (String) session.getAttribute(usersession);
            session.setAttribute("bookid", bookid);
            session.setAttribute(reqType, "delivery");
            return requestService.placeRequest(username, bookid);
        } catch (ServiceException e) {
            LOG.error("Could not place request - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Confirm.
     * 
     * @param session
     *            the session
     * @param model
     *            the model
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/confirm")
    public String confirm(HttpSession session, Model model,
            HttpServletRequest request) {
        try {
            LOG.debug("Confirming user address");
            String username = (String) session.getAttribute(usersession);
            String address = userService.address(username);
            model.addAttribute("address", address);
            return "ConfirmAddress";
        } catch (ServiceException e) {
            LOG.error("Could not get user address - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Prevaddr.
     * 
     * @param session
     *            the session
     * @param model
     *            the model
     * @param response
     *            the response
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/placeprev")
    public String prevaddr(HttpSession session, Model model,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            LOG.debug("Placing delivery request with old address");

            String username = (String) session.getAttribute(usersession);
            int bookid = (Integer) session.getAttribute("bookid");

            String address = userService.address(username);
            requestService.saveRequest(username, bookid, address);
            session.setAttribute(placed,
                    "Book will be delivered to the old address");
            LOG.debug("Delivery request placed for " + bookid + " by "
                    + username);
            mailService.sendMail(session);
            LOG.debug(mailSentTo + username + " for delivery of " + bookid);
            return "redirect:showBookshelf";
        } catch (ServiceException e) {
            LOG.error("Could not place delivery request - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Newaddr.
     * 
     * @param address
     *            the address
     * @param session
     *            the session
     * @param model
     *            the model
     * @param response
     *            the response
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/placenew", method = RequestMethod.POST)
    public String newaddr(@RequestParam("address") String address,
            HttpSession session, Model model, HttpServletResponse response,
            HttpServletRequest request) {
        try {
            LOG.debug("Placing delivery request with new address");

            String username = (String) session.getAttribute(usersession);
            int bookid = (Integer) session.getAttribute("bookid");

            requestService.saveRequest(username, bookid, address);
            session.setAttribute(placed,
                    "Book will be delivered to the new address");
            LOG.debug("Delivery request placed for " + bookid + " by "
                    + username);
            mailService.sendMail(session);
            LOG.debug(mailSentTo + username + " for delivery of " + bookid);

            return "redirect:showBookshelf";
        } catch (ServiceException e) {
            LOG.error("Could not place delivery request - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Return request.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/returnRequest", method = RequestMethod.GET)
    public @ResponseBody
    String returnRequest(@RequestParam("bookid") int bookid, Model model,
            HttpSession session, HttpServletRequest request) {
        try {
            LOG.debug("Return Request for bookid = " + bookid);
            String username = (String) session.getAttribute(usersession);
            session.setAttribute("bookid", bookid);
            session.setAttribute(reqType, "return");

            requestService.returnBook(username, bookid);
            LOG.debug(username + " placed return request for " + bookid);

            mailService.sendMail(session);
            LOG.debug(mailSentTo + username + " for delivery of " + bookid);

            return "Return request placed !";
        } catch (ServiceException e) {
            LOG.error("Could not place return request - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Cancel request.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/cancelRequest", method = RequestMethod.GET)
    public @ResponseBody
    String cancelRequest(@RequestParam("bookid") int bookid, Model model,
            HttpSession session, HttpServletRequest request) {
        try {
            LOG.debug("Canceling return request for " + bookid);

            String username = (String) session.getAttribute(usersession);
            session.setAttribute("bookid", bookid);
            session.setAttribute(reqType, "cancel return");
            LOG.debug("Return request cancelled for " + bookid + " by "
                    + username);
            mailService.sendMail(session);
            LOG.debug(mailSentTo + username + " for cancelling return of "
                    + bookid);

            return requestService.cancelReturn(username, bookid);
        } catch (ServiceException e) {
            LOG.error("Could not cancel return request - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Cancel delivery.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/cancelDelivery", method = RequestMethod.GET)
    public @ResponseBody
    String cancelDelivery(@RequestParam("bookid") int bookid, Model model,
            HttpSession session, HttpServletRequest request) {
        try {
            LOG.debug("Canceling Delivery Request for " + bookid);

            String username = (String) session.getAttribute(usersession);
            session.setAttribute("bookid", bookid);
            session.setAttribute(reqType, "cancel delivery");
            requestService.cancelDelivery(username, bookid);
            LOG.debug(username + " placed delivery request for " + bookid);
            mailService.sendMail(session);
            LOG.debug(mailSentTo + username + " for cancelling delivery of "
                    + bookid);
            return "Delivery request cancelled !";
        } catch (ServiceException e) {
            LOG.error("Could not cancel delivery request - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Adds the to bookshelf.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the string
     */
    @RequestMapping(value = "/addToBookshelf", method = RequestMethod.GET)
    public @ResponseBody
    String addToBookshelf(@RequestParam("bookid") int bookid, Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            LOG.debug("Adding " + bookid + " to bookshelf..");
            session.setAttribute(reqType, "bookshelf");
            session.setAttribute("bookid", bookid);
            String username = (String) session.getAttribute(usersession);
            return requestService.addToBookshelf(username, bookid);
        } catch (ServiceException e) {
            LOG.error("Could not add to bookshelf - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Removes the bookshelf.
     * 
     * @param bookid
     *            the bookid
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/removeBookshelf", method = RequestMethod.GET)
    public @ResponseBody
    String removeBookshelf(@RequestParam("bookid") int bookid, Model model,
            HttpSession session, HttpServletRequest request) {
        try {
            String username = (String) session.getAttribute(usersession);
            requestService.removeBookshelf(username, bookid);
            LOG.debug("Book " + bookid + " removed from bookshelf by "
                    + username);
            return "Book removed from Bookshelf !";
        } catch (ServiceException e) {
            LOG.error("Could not remove from bookshelf - "
                    + e.getExceptionMsg() + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

    /**
     * Show bookshelf.
     * 
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/showBookshelf")
    public String showBookshelf(Model model, HttpSession session,
            HttpServletRequest request) {

        String username = (String) session.getAttribute(usersession);
        LOG.debug("Displaying bookshelf for " + username);
        String add = (String) session.getAttribute(placed);
        model.addAttribute("add", add);
        session.removeAttribute(placed);
        try {
            List userReq1 = requestService.booksHolding(username);
            model.addAttribute("userReq1", userReq1);
            List holding = requestService.showCurrent(username);
            model.addAttribute("holding", holding);
            LOG.debug("Currently reading books retrieved for " + username);
            if (holding.isEmpty()) {
                model.addAttribute("current", noBooksToDisplay);
            }

            List userReq2 = requestService.booksToReturn(username);
            model.addAttribute("userReq2", userReq2);
            List toReturn = requestService.toReturn(username);
            model.addAttribute("toReturn", toReturn);
            LOG.debug("To be returned books retrieved for " + username);
            if (toReturn.isEmpty()) {
                model.addAttribute("toreturn", noBooksToDisplay);
            }

            List userReq3 = requestService.booksToDeliver(username);
            model.addAttribute("userReq3", userReq3);
            List toDeliver = requestService.toDeliver(username);
            model.addAttribute("toDeliver", toDeliver);
            LOG.debug("To be read books retrieved for " + username);
            if (toDeliver.isEmpty()) {
                model.addAttribute("willread", noBooksToDisplay);
            }

            List userReq4 = requestService.booksReturned(username);
            model.addAttribute("userReq4", userReq4);
            List returned = requestService.returned(username);
            model.addAttribute("returned", returned);
            LOG.debug("Already read books retrieved for " + username);
            if (returned.isEmpty()) {
                model.addAttribute("done", noBooksToDisplay);
            }

            List mylist = requestService.showBookshelf(username);
            model.addAttribute("mylist", mylist);
            LOG.debug("Bookshelf books retrieved for " + username);

            if (mylist.isEmpty()) {
                model.addAttribute(message, noBooksToDisplay);
                return "Bookshelf";
            } else {
                return "Bookshelf";
            }
        } catch (ServiceException e) {
            LOG.error("Could not display bookshelf - " + e.getExceptionMsg()
                    + cause + e.getExceptionCause());
            model.addAttribute(message, e.getExceptionMsg());
            return redirectExhandler;
        }
    }

}