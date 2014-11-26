package com.impetus.bookstore.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.impetus.bookstore.model.BookDetails;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchService.
 */
@WebService
public interface SearchService {

    /**
     * Searchbooks.
     * 
     * @param book
     *            the book
     * @return the list
     */
    @WebMethod
    List<BookDetails> search(String book);
}
