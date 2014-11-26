package com.impetus.bookstore.controller.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.impetus.bookstore.controller.BooksController;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.service.BooksService;

public class BooksControllerTest {

    private MockMvc mockMvc;
    @Mock
    private BooksService booksService;
    @Mock
    private MockHttpServletRequest request;
    @Mock
    private MockHttpSession session;
    @Mock
    private MockMultipartFile file;
    @InjectMocks
    private BooksController booksController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }

    @Test
    public void testFindbooks() throws Exception {
        String book = "book";
        BookDetails bd = new BookDetails();
        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);

        when(booksService.findAdm(book)).thenReturn(books);
        mockMvc.perform(get("/find")
                .param("book", "book"))         
                .andExpect(status().isOk())
                .andExpect(view().name("UpdDelBook"))
                .andExpect(forwardedUrl("UpdDelBook"));
        verify(booksService, times(1)).findAdm(book);
        assertEquals(books,booksService.findAdm(book));
    }

    @Test
    public void testUpdtbook() throws Exception {
        BookDetails bd = new BookDetails();
        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);

        when(booksService.requestedBook(1)).thenReturn(books);
        mockMvc.perform(get("/toupdate")
                .param("bookid", "1"))         
                .andExpect(status().isOk())
                .andExpect(view().name("UpdtBook"))
                .andExpect(forwardedUrl("UpdtBook"));
        verify(booksService, times(1)).requestedBook(1);
    }

    @Test
    public void testUpdate() throws Exception {
        BookDetails bd = new BookDetails();
        when(booksService.update(bd)).thenReturn(true);
        mockMvc.perform(post("/update"))         
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("userhome?status=The+book+has+been+updated"));
        verify(booksService, times(1)).update(bd);
    }

    @Test
    public void testAdd() throws Exception {
        BookDetails bd = new BookDetails();
        when(booksService.add(bd)).thenReturn(true);
        mockMvc.perform(post("/add"))         
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("userhome?status=The+book+has+been+added"));
        verify(booksService, times(1)).add(bd);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGeneratePdf() throws Exception {
        Date from = new Date();
        Date to = new Date();
        String author = "author";
        String category = "category";
        BookDetails bd = new BookDetails();

        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);
        List count = new ArrayList();
        count.add(1);
        Map<String, List> m = new HashMap<String, List>();
        m.put("books",books);
        m.put("count",count);

        when(booksService.summary(from, to, author, category)).thenReturn(m);
        mockMvc.perform(post("/generatepdf")
                .param("from", "from")
                .param("to", "to")
                .param("author", "author")
                .param("category", "category")
                .param("model", "model")
                .param("map", "map"))         
                .andExpect(status().is(400))
                .andExpect(view().name("pdfView"));
        verify(booksService, times(1)).summary(from, to, author, category);
    }

    @Test
    public void testSearch() throws Exception {
        String book = "book";
        BookDetails bd = new BookDetails();

        when(booksService.search(book)).thenReturn(Arrays.asList(bd));
        mockMvc.perform(get("/displayBooks")
                .param("book", book))         
                .andExpect(status().isOk())
                .andExpect(view().name("DisplayBooks"))
                .andExpect(forwardedUrl("DisplayBooks"));
        verify(booksService, times(1)).search(book);
    }

    @Test
    public void testRequestedBook() throws Exception {
        BookDetails bd = new BookDetails();
        when(booksService.requestedBook(1)).thenReturn(Arrays.asList(bd));
        mockMvc.perform(get("/requestedBook")
                .param("bookid", "1"))         
                .andExpect(status().isOk())
                .andExpect(view().name("RequestedBook"))
                .andExpect(forwardedUrl("RequestedBook"));
        verify(booksService, times(1)).requestedBook(1);
    }

}
