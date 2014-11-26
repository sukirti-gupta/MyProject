package com.impetus.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.impetus.bookstore.dao.SearchDao;
import com.impetus.bookstore.model.BookDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchServiceImpl.
 */
@WebService(endpointInterface = "com.impetus.bookstore.service.SearchService")
public class SearchServiceImpl implements SearchService {

    /** The s. */
    private SearchDao searchDao = new SearchDao();

    /**
     * Sets the dao.
     * 
     * @param searchDao
     *            the new dao
     */
    public void setDAO(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.impetus.onlinelibrary.service.SearchServiceSoap#searchbooks(java.
     * lang.String)
     */
    @Override
    public List<BookDetails> search(String book) {

        List<BookDetails> books = new ArrayList<BookDetails>();
        books = searchDao.search(book);
        return books;
    }
}
