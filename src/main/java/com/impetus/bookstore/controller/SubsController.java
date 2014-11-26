package com.impetus.bookstore.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.impetus.bookstore.service.Subservice;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;

// TODO: Auto-generated Javadoc
/**
 * The Class SubsController.
 */
@RequestMapping("/")
@Controller
public class SubsController {

    /** The subservice. */
    @Autowired
    private Subservice subservice;

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(SubsController.class);

    /** The failed to upload. */
    private String failedToUpload = "Failed to upload ";

    /** The status. */
    private String status = "status";

    /**
     * Sub.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/subscription")
    public String sub(Model model) {
        try {
            List<Subscriptions> subs = subservice.getSub();
            model.addAttribute("subs", subs);
            LOG.debug("Viewing subscriptions");
            return "Subscriptions";
        } catch (ServiceException e) {
            LOG.error("Could not load page - " + e.getExceptionMsg()
                    + " Cause : " + e.getExceptionCause());
            model.addAttribute("message", e.getExceptionMsg());
            return "redirect:exhandler";
        }
    }

    /**
     * Adds the sub.
     * 
     * @param subid
     *            the subid
     * @param session
     *            the session
     * @param model
     *            the model
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public @ResponseBody
    String addSub(@RequestParam("subid") int subid, HttpSession session,
            Model model) throws IOException {
        try {
            String username = (String) session.getAttribute("usersession");
            subservice.updtSub(username, subid);
            model.addAttribute("updated", "Subscription plan has been updated.");
            LOG.debug(username + " updated subscription plan to " + subid);
            return "updated";
        } catch (ServiceException e) {
            LOG.error("Could not update subscription plan - "
                    + e.getExceptionMsg() + " Cause : " + e.getExceptionCause());
            model.addAttribute("message", "Could not update subscription plan");
            return "redirect:subscription";
        }
    }

    /**
     * Adds the or update subscription.
     * 
     * @param file
     *            the file
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/updsub", method = RequestMethod.POST)
    public String addOrUpdateSubscription(
            @RequestParam("file") MultipartFile file, Model model) {

        String fileName = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                String uploadStatus = subservice.addOrUpdateSubscription(file);
                model.addAttribute(status, uploadStatus);
            } catch (Exception e) {
                model.addAttribute(status, "Error occured. Failed to upload "
                        + fileName);
                LOG.error(failedToUpload + fileName + "=> " + e.getMessage());
            }
        } else {
            model.addAttribute(status, failedToUpload + fileName
                    + " because the file was empty.");
            LOG.error(failedToUpload + fileName + " because it was empty");
        }
        return "redirect:userhome";
    }

}
