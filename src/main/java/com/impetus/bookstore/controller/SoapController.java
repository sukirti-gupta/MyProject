package com.impetus.bookstore.controller;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.service.BooksService;
import com.impetus.bookstore.service.SearchService;

// TODO: Auto-generated Javadoc
/**
 * The Class SoapController.
 */
@RequestMapping("/")
@Controller
public class SoapController {

    /** The books service. */
    @Autowired
    private BooksService booksService;

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(BooksController.class);

    /**
     * Soap.
     * 
     * @return the string
     */
    @RequestMapping(value = "/webService")
    public String soap() {
        return "SoapSearch";
    }

    /**
     * List.
     * 
     * @param book
     *            the book
     * @param model
     *            the model
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the string
     */
    @RequestMapping(value = "/searchBook")
    public String list(@RequestParam("book") String book, Model model,
            HttpSession session, HttpServletRequest request) {
        try {
            session.getAttribute("usersession");
            model.addAttribute("category", book);
            session.setAttribute("book", book);

            List<BookDetails> books = booksService.search(book);
            if (books.isEmpty()) {
                model.addAttribute("error", "No books found.");
                LOG.debug("Books not found for " + book);
            } else {
                model.addAttribute("books", books);
                LOG.debug("Displaying books for " + book);
            }

            URL wsdlUrl = new URL(
                    "http://localhost:8882/webservice/search?wsdl");
            QName qname = new QName("http://service.bookstore.impetus.com/",
                    "SearchServiceImplService");
            Service service = Service.create(wsdlUrl, qname);
            SearchService search = service.getPort(SearchService.class);
            books = search.search(book);
            model.addAttribute("books", books);
            return "SoapSearch";
        } catch (Exception e) {
            LOG.error("Could not search book - " + e.getMessage() + " Cause : "
                    + e.getCause());
            model.addAttribute("message", e.getMessage());
            return "redirect:exhandler";
        }
    }
}
