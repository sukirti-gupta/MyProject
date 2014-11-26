package com.impetus.bookstore.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.impetus.bookstore.controller.UserController;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserSubs;
import com.impetus.bookstore.service.Subservice;
import com.impetus.bookstore.service.UserService;

public class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private Subservice subservice;
    @Mock
    private MockHttpSession session;
    @Mock
    private MockHttpServletRequest request;
    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetLoginForm() throws Exception {
        mockMvc.perform(get("/login2")
                .param("authfailed", "abc")
                .session(session))           
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("Login"))
                .andExpect(forwardedUrl("Login"));
    }
    
    @Test
    public void testGetLoginForm2() throws Exception {
        mockMvc.perform(get("/login2")
                .param("logout", "abc")
                .session(session))           
                .andExpect(status().is(302))
                .andExpect(model().attributeExists("message"))
                .andExpect(redirectedUrl("home?message=Logged+out+successfully"));
    }
    
    @Test
    public void testGetLoginForm3() throws Exception {
        mockMvc.perform(get("/login2")
                .param("denied", "abc")
                .session(session))           
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("Login"))
                .andExpect(forwardedUrl("Login"));
    }

    @Test
    public void testProfile() throws Exception {
        session.setAttribute("usersession", "abc");
        String username = "abc";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date endDate = cal.getTime();
        
        UserDetails ud = new UserDetails();
        ud.setAddress("xyz city");
        ud.setContact("1234567890");
        ud.setEid("abc@gamil.com");
        ud.setLanguage("english");
        ud.setName("abc");
        ud.setPassword("abc");
        ud.setUsername("abc");
        
        Subscriptions sub = new Subscriptions();
        sub.setSubid(1);
        sub.setSubName("Basic");
        sub.setSubAmount(100);
        sub.setMaxBooks(5);
        sub.setSubDuration(1);
        sub.setValidity(1);
        
        UserSubs us = new UserSubs();
        us.setBooksLeft(5);
        us.setDays(30);
        us.setEndDate(endDate);
        us.setStartDate(date);
        us.setSubid(1);
        us.setUsername("abc");
        us.setUsersubid(12);
        us.setValid(true);
        
        when(session.getAttribute("usersession")).thenReturn("abc");
        when(userService.user(username)).thenReturn(Arrays.asList(ud));
        when(subservice.userSub(username)).thenReturn(Arrays.asList(us));
        when(subservice.subDetails(username)).thenReturn(Arrays.asList(sub));
        mockMvc.perform(get("/profile")
                .session(session))           
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user",Arrays.asList(ud)))
                .andExpect(model().attributeExists("usersub"))
                .andExpect(model().attribute("usersub",Arrays.asList(us)))
                .andExpect(model().attributeExists("subDetails"))
                .andExpect(model().attribute("subDetails",Arrays.asList(sub)))
                .andExpect(view().name("Profile"))
                .andExpect(forwardedUrl("Profile"));
    }

    @Test
    public void testLogout() throws Exception {
        when(request.getSession()).thenReturn(session);
        mockMvc.perform(get("/logout")
                .session(session))           
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message","Logged out successfully, login again to continue !"))
                .andExpect(view().name("Home"))
                .andExpect(forwardedUrl("Home"));
    }

    @Test
    public void testReqStat() throws Exception {
        
        when(userService.chngReqStat(1)).thenReturn(true);
        mockMvc.perform(get("/reqStat")
                .param("reqid", "1"))           
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("userReq"));
    }

}
