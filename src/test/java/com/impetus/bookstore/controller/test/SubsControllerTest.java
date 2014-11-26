package com.impetus.bookstore.controller.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

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

import com.impetus.bookstore.controller.SubsController;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.service.Subservice;

public class SubsControllerTest {
    
    private MockMvc mockMvc;
    @Mock
    private Subservice subservice;
    @Mock
    private MockHttpServletRequest request;
    @Mock
    private MockHttpSession session;
    @Mock
    private MockMultipartFile file;
    @InjectMocks
    private SubsController subsController;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subsController).build();
    }

    @Test
    public void testSub() throws Exception {
        Subscriptions sub = new Subscriptions();
        sub.setSubid(1);
        sub.setSubName("Basic");
        sub.setSubAmount(100);
        sub.setMaxBooks(5);
        sub.setSubDuration(1);
        sub.setValidity(1);
        
        when(subservice.getSub()).thenReturn(Arrays.asList(sub));
        mockMvc.perform(get("/subscription"))           
        .andExpect(status().isOk())
        .andExpect(view().name("Subscriptions"))
        .andExpect(forwardedUrl("Subscriptions"));
        verify(subservice, times(1)).getSub();
        assertEquals(Arrays.asList(sub),subservice.getSub());
    }

}
