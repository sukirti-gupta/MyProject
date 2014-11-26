package com.impetus.bookstore.controller;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.impetus.bookstore.service.RecommService;
import com.impetus.bookstore.service.UserService;
import com.impetus.bookstore.service.Subservice;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserDetails;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class UserController.
 */
@RequestMapping("/")
@Controller
public class UserController {

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The subservice. */
    @Autowired
    private Subservice subservice;

    /** The recomm service. */
    @Autowired
    private RecommService recommService;

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(UserController.class);

    /** The login page. */
    private String loginPage = "Login";

    /** The cause. */
    private String cause = " Cause : ";

    /** The register page. */
    private String registerPage = "Register";

    /** The msg. */
    private String msg = "message";

    /** The sub model. */
    private String subModel = "subs";

    /**
     * Gets the login form.
     * 
     * @param authfailed
     *            the authfailed
     * @param logout
     *            the logout
     * @param denied
     *            the denied
     * @param session
     *            the session
     * @param request
     *            the request
     * @return the login form
     */
    @RequestMapping("/login2")
    public ModelAndView getLoginForm(
            @RequestParam(required = false) String authfailed, String logout,
            String denied, HttpSession session, HttpServletRequest request) {
        try {
            String message = "";
            if (authfailed != null) {
                message = "Invalid username or password, try again !";
                LOG.debug("Invalid credentials");
                return new ModelAndView(loginPage, msg, message);
            } else if (logout != null) {
                message = "Logged out successfully";
                LOG.info(message);
                return new ModelAndView("redirect:home", msg, message);
            } else if (denied != null) {
                message = "Access denied for this user !";
                LOG.debug("Access denied");
                return new ModelAndView(loginPage, msg, message);
            }
            return new ModelAndView(loginPage, msg, message);
        } catch (Exception e) {
            return new ModelAndView(loginPage, msg,
                    "An error occured. Please try again");
        }
    }

    /**
     * Get user page.
     * 
     * @param session
     *            the session
     * @param request
     *            the request
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping("/user")
    public String getUserPage(HttpSession session, HttpServletRequest request,
            Model model) {
        try {
            String username = request.getUserPrincipal().getName();
            session.setAttribute("usersession", username);
            LOG.debug(username + " logged in successfuly");

            String book = (String) session.getAttribute("book");
            if (session.getAttribute("book") == null) {
                return "redirect:userhome";
            } else {
                return "redirect:displayBooks?book=" + book;
            }
        } catch (Exception e) {
            LOG.error("Could not return user page " + e.getMessage() + cause
                    + e.getCause());
            model.addAttribute("message", "An error occured. Please try again");
            return "redirect:exhandler";
        }
    }

    /**
     * Adds the user.
     * 
     * @param ud
     *            the ud
     * @param bindingResult
     *            the binding result
     * @param confirmPassword
     *            the confirm password
     * @param subscription
     *            the subscription
     * @param model
     *            the model
     * @param session
     *            the session
     * @return the string
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(
            @ModelAttribute("ud") UserDetails ud,
            BindingResult bindingResult,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam(required = false, value = "subscription") String subscription,
            Model model, HttpSession session) {
        try {
            LOG.debug("Registering user");
            if (subscription == null) {
                model.addAttribute("emptySub", "Choose a subscription plan");
                List<Subscriptions> subs = subservice.getSub();
                model.addAttribute(subModel, subs);
                return registerPage;
            }
            if (userService.email(ud.getEid())) {
                List<UserDetails> user = userService.user(ud.getUsername());
                if (user.isEmpty()) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String password = ud.getPassword();
                    password = passwordEncoder.encode(password);
                    userService.save(ud, password, subscription);
                    recommService.randomrecomm(ud.getUsername());
                    LOG.debug(ud.getUsername() + " registered successfully");
                    model.addAttribute("login",
                            "Successfully registered. Please login to continue.");
                    return loginPage;
                } else {
                    model.addAttribute("exists", "Username already taken");
                    List<Subscriptions> subs = subservice.getSub();
                    model.addAttribute(subModel, subs);
                    return registerPage;
                }
            } else {
                model.addAttribute("email", "User already registered.");
                List<Subscriptions> subs = subservice.getSub();
                model.addAttribute(subModel, subs);
                return registerPage;
            }
        } catch (ServiceException e) {
            LOG.error("Could not register user " + e.getExceptionMsg() + cause
                    + e.getExceptionCause());
            model.addAttribute(msg, e.getExceptionMsg());
            return "redirect:homeToRegister";
        } catch (Exception e) {
            LOG.error("Encountered an issue with the encryption algorithm "
                    + e.getMessage() + cause + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "redirect:homeToRegister";
        }
    }

    /**
     * Register admin.
     * 
     * @param ud
     *            the ud
     * @param bindingResult
     *            the binding result
     * @param model
     *            the model
     * @param session
     *            the session
     * @return the string
     */
    @RequestMapping(value = "/registerAdmin", method = RequestMethod.POST)
    public String registerAdmin(@ModelAttribute("ud") UserDetails ud,
            BindingResult bindingResult, Model model, HttpSession session) {
        try {
            if (userService.email(ud.getEid())) {
                List<UserDetails> user = userService.user(ud.getUsername());
                if (user.isEmpty()) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String password = ud.getPassword();
                    password = passwordEncoder.encode(password);
                    userService.saveAdmin(ud, password);
                    LOG.debug(ud.getUsername()
                            + " registered successfully as admin");
                    model.addAttribute("status",
                            "Successfully registered new Admin");
                    return "redirect:userhome";
                } else {
                    model.addAttribute("exists", "Username already taken");
                    return "redirect:userhome";
                }
            } else {
                model.addAttribute("email", "User already registered.");
                return "redirect:userhome";
            }
        } catch (ServiceException e) {
            LOG.error("Could not register new admin " + e.getMessage() + cause
                    + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "redirect:userhome";
        }
    }

    /**
     * Profile.
     * 
     * @param session
     *            the session
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/profile")
    public String profile(HttpSession session, Model model) {
        try {
            String username = (String) session.getAttribute("usersession");
            List<UserDetails> user = userService.user(username);
            model.addAttribute("user", user);
            List<UserSubs> usersub = subservice.userSub(username);
            model.addAttribute("usersub", usersub);
            List<Subscriptions> subDetails = subservice.subDetails(username);
            model.addAttribute("subDetails", subDetails);
            LOG.debug("Displaying profile information for " + username);
            return "Profile";
        } catch (ServiceException e) {
            LOG.error("Could not retrieve user profile " + e.getMessage()
                    + cause + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "redirect:userhome";
        }
    }

    /**
     * Logout.
     * 
     * @param session
     *            the session
     * @param request
     *            the request
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest request,
            Model model) {
        request.getSession();
        session.invalidate();
        model.addAttribute(msg,
                "Logged out successfully, login again to continue !");
        LOG.debug("Logged out succeffully");
        return "Home";
    }

    /**
     * User info.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/userInfo")
    public String userInfo(Model model) {
        try {
            Map m = userService.activeUser();
            List usersub = (List) m.get("usersub");
            List userdet = (List) m.get("userdet");
            model.addAttribute("sub", usersub);
            model.addAttribute("det", userdet);
            LOG.debug("Viewing users with active subscriptions");
            return "UserInfo";
        } catch (ServiceException e) {
            LOG.error("Could not retrieve active users " + e.getMessage()
                    + cause + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "UserInfo";
        }
    }

    /**
     * User req.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/userReq")
    public String userReq(Model model) {
        try {
            Map m = userService.userReq();
            List names = (List) m.get("users");
            List userReq = (List) m.get("userReq");
            model.addAttribute("name", names);
            model.addAttribute("req", userReq);
            LOG.info("Viewing user requests and their status");
            return "UserReq";
        } catch (ServiceException e) {
            LOG.error("Could not retrieve users request details "
                    + e.getMessage() + cause + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "UserReq";
        }
    }

    /**
     * Req stat.
     * 
     * @param reqid
     *            the reqid
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/reqStat")
    public String reqStat(int reqid, Model model) {
        try {
            userService.chngReqStat(reqid);
            LOG.info("User request closed");
            return "redirect:userReq";
        } catch (ServiceException e) {
            LOG.error("Could not change request status " + e.getMessage()
                    + cause + e.getCause());
            model.addAttribute(msg, e.getMessage());
            return "redirect:userReq";
        }
    }

}
