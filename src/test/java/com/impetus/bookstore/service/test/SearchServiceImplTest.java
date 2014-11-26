package com.impetus.bookstore.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.impetus.bookstore.dao.SearchDao;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.service.SearchServiceImpl;

public class SearchServiceImplTest {

    private SearchDao searchDao;
    SearchServiceImpl searchServiceImpl = new SearchServiceImpl();

    @Before
    public void setUp() {
        searchDao = mock(SearchDao.class);
    }

    @Test
    public void test() throws Exception {

        searchServiceImpl.setDAO(searchDao);
        BookDetails bd = new BookDetails();

        when(searchDao.search("the")).thenReturn(Arrays.asList(bd));
        List<BookDetails> actual = searchServiceImpl.search("the");
        assertEquals(Arrays.asList(bd), actual);

    }

}
