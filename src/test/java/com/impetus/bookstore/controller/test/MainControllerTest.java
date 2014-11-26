package com.impetus.bookstore.controller.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.impetus.bookstore.controller.MainController;
import com.impetus.bookstore.model.BookDetails;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.service.BooksService;
import com.impetus.bookstore.service.RecommService;
import com.impetus.bookstore.service.Subservice;

public class MainControllerTest {

    private MockMvc mockMvc;
    @Mock
    private BooksService booksService;
    @Mock
    private Subservice subservice;
    @Mock
    private RecommService recommService;
    @Mock
    private MockHttpServletRequest request;
    @Mock
    private MockHttpSession session;
    @InjectMocks
    private MainController mainController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void testHome2() throws Exception {
        Date date = new Date();
        BookDetails bd = new BookDetails();
        bd.setAuthor("author");
        bd.setAvail(10);
        bd.setBookid(1);
        bd.setCategory("category");
        bd.setDateAdded(date);
        bd.setDescription("description");
        bd.setExist("true");
        bd.setImage("image");
        bd.setLanguage("language");
        bd.setName("name");
        bd.setPublisher("publisher");
        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);

        when(recommService.newbook()).thenReturn(books);
        when(recommService.popular()).thenReturn(books);
        mockMvc.perform(get("/home"))         
        .andExpect(status().isOk())
        .andExpect(view().name("Home"))
        .andExpect(forwardedUrl("Home"));
        verify(recommService, times(1)).popular();
        verify(recommService, times(1)).newbook();
        assertEquals(books,recommService.popular());
        assertEquals(books,recommService.newbook());
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/homeToLogin"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Login"))
        .andExpect(forwardedUrl("Login"));
    }

    @Test
    public void testRegister() throws Exception {
        Subscriptions sub = new Subscriptions();
        sub.setSubid(1);
        sub.setSubName("Basic");
        sub.setSubAmount(100);
        sub.setMaxBooks(5);
        sub.setSubDuration(1);
        sub.setValidity(1);
        
        List<Subscriptions> subs = new ArrayList<Subscriptions>();
        subs.add(sub);
        
        when(subservice.getSub()).thenReturn(subs);
        mockMvc.perform(get("/homeToRegister"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Register"))
        .andExpect(forwardedUrl("Register"));
        verify(subservice, times(1)).getSub();
        assertEquals(subs,subservice.getSub());
    }

    @Test
    public void testUserhome() throws Exception {
        session.setAttribute("usersession", "abc");
        String username = "abc";
        Date date = new Date();
        BookDetails bd = new BookDetails();
        bd.setAuthor("author");
        bd.setAvail(10);
        bd.setBookid(1);
        bd.setCategory("category");
        bd.setDateAdded(date);
        bd.setDescription("description");
        bd.setExist("true");
        bd.setImage("image");
        bd.setLanguage("language");
        bd.setName("name");
        bd.setPublisher("publisher");;;
        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);
        
        List<String> category = new ArrayList<String>();
        category.add("category");
        List<String> author = new ArrayList<String>();
        author.add("author");
        
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usersession")).thenReturn("abc");
        when(recommService.newbook()).thenReturn(books);
        when(recommService.popular()).thenReturn(books);
        when(recommService.recombooks("abc")).thenReturn(books);
        when(recommService.randomrecomm("abc")).thenReturn(true);
        mockMvc.perform(get("/userhome")
        .session(session)
        .param("status", "done"))
        .andExpect(status().isOk())
        .andExpect(view().name("userHome"))
        .andExpect(forwardedUrl("userHome"));
        verify(recommService, times(1)).popular();
        verify(recommService, times(1)).newbook();
        verify(recommService, times(1)).recombooks(username);
        assertEquals(books,recommService.popular());
        assertEquals(books,recommService.newbook());
        assertEquals(books,recommService.recombooks(username));
    }
    
    @Test
    public void testUserhomeBranch() throws Exception {
        session.setAttribute("usersession", "abc");
        String username = "abc";
        Date date = new Date();
        BookDetails bd = new BookDetails();
        bd.setAuthor("author");
        bd.setAvail(10);
        bd.setBookid(1);
        bd.setCategory("category");
        bd.setDateAdded(date);
        bd.setDescription("description");
        bd.setExist("true");
        bd.setImage("image");
        bd.setLanguage("language");
        bd.setName("name");
        bd.setPublisher("publisher");;;
        List<BookDetails> books = new ArrayList<BookDetails>();
        books.add(bd);
        List<BookDetails> books2 = new ArrayList<BookDetails>();
        
        List<String> category = new ArrayList<String>();
        category.add("category");
        List<String> author = new ArrayList<String>();
        author.add("author");
        
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usersession")).thenReturn("abc");
        when(recommService.newbook()).thenReturn(books);
        when(recommService.popular()).thenReturn(books);
        when(recommService.recombooks("abc")).thenReturn(books2);
        when(recommService.randomrecomm("abc")).thenReturn(true);
        mockMvc.perform(get("/userhome")
        .session(session)
        .param("status", "done"))
        .andExpect(status().isOk())
        .andExpect(view().name("userHome"))
        .andExpect(forwardedUrl("userHome"));
        verify(recommService, times(1)).popular();
        verify(recommService, times(1)).newbook();
        verify(recommService, times(1)).recombooks(username);
        assertEquals(books,recommService.popular());
        assertEquals(books,recommService.newbook());
        assertEquals(books2,recommService.recombooks(username));
        assertEquals(true,recommService.randomrecomm(username));
        
    }

    @Test
    public void testExhandler() throws Exception {
        mockMvc.perform(get("/exhandler"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Error"))
        .andExpect(forwardedUrl("Error"));
    }

    @Test
    public void testError404() throws Exception {
        mockMvc.perform(get("/404"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Error"))
        .andExpect(forwardedUrl("Error"))
        .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testErr404() throws Exception {
        mockMvc.perform(post("/404"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Error"))
        .andExpect(forwardedUrl("Error"))
        .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testError500() throws Exception {
        mockMvc.perform(get("/500"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Error"))
        .andExpect(forwardedUrl("Error"))
        .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testErr500() throws Exception {
        mockMvc.perform(post("/500"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Error"))
        .andExpect(forwardedUrl("Error"))
        .andExpect(model().attributeExists("error"));
    }

}
