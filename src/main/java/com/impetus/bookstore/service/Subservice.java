package com.impetus.bookstore.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.impetus.bookstore.dao.SubscriptionDAO;
import com.impetus.bookstore.exceptions.DAOException;
import com.impetus.bookstore.exceptions.ServiceException;
import com.impetus.bookstore.model.Subscriptions;
import com.impetus.bookstore.model.UserSubs;

// TODO: Auto-generated Javadoc
/**
 * The Class Subservice.
 */
@Service
public class Subservice {

    /** The Subscription dao. */
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    /**
     * Sets the dao.
     * 
     * @param subscriptionDAO
     *            the new dao
     */
    public void setDAO(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(Subservice.class);

    /**
     * Ifbooksleft.
     * 
     * @param username
     *            the username
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean ifbooksleft(String username) throws ServiceException {
        int booksLeft = 0;
        try {
            List<UserSubs> userSub = subscriptionDAO.getUserSub(username);
            for (UserSubs det : userSub) {
                booksLeft = det.getBooksLeft();
            }
            if (booksLeft > 0) {
                LOG.debug(username + " can order more books");
                return true;
            } else {
                LOG.debug(username + " cannot order more books");
                return false;
            }
        } catch (DAOException e) {
            LOG.error("Could not retrieve user details");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Adds the sub.
     * 
     * @param username
     *            the username
     * @param subscription
     *            the subscription
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean addSub(String username, String subscription)
            throws ServiceException {
        int id = 0, dur = 0, max = 0;
        try {
            List<Subscriptions> subs = subscriptionDAO.getSub();
            for (Subscriptions sub : subs) {
                id = sub.getSubid();
                dur = sub.getSubDuration();
                max = sub.getMaxBooks();
            }
            subscriptionDAO.invalidateSub(username);
            subscriptionDAO.addSub(username, id, dur, max, subscription);
            LOG.debug("Subscription plan " + id + " added for " + username);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not add subscription plan");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Updt sub.
     * 
     * @param username
     *            the username
     * @param subid
     *            the subid
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public boolean updtSub(String username, int subid) throws ServiceException {
        String subName = "";
        int id = 0, dur = 0, max = 0;
        try {
            List<Subscriptions> sub = subscriptionDAO.subDetails(subid);
            for (Subscriptions subs : sub) {
                subName = subs.getSubName();
                id = subs.getSubid();
                dur = subs.getSubDuration();
                max = subs.getMaxBooks();
            }
            subscriptionDAO.invalidateSub(username);
            subscriptionDAO.addSub(username, id, dur, max, subName);
            LOG.debug("Subscription plan " + id + " updated for " + username);
            return true;
        } catch (DAOException e) {
            LOG.error("Could not update subscription plan");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * User sub.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<UserSubs> userSub(String username) throws ServiceException {
        try {
            LOG.debug("Returning subscription details for " + username);
            return subscriptionDAO.userSub(username);
        } catch (DAOException e) {
            LOG.error("Could not retrieve subscription details ");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Sub_details.
     * 
     * @param username
     *            the username
     * @return the list
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<Subscriptions> subDetails(String username)
            throws ServiceException {
        int subid = 0;
        try {
            List<UserSubs> usersub = subscriptionDAO.userSub(username);
            for (UserSubs user : usersub) {
                subid = user.getSubid();
            }
            LOG.debug("Returning subscription details for id " + subid);
            return subscriptionDAO.subDetails(subid);
        } catch (DAOException e) {
            LOG.error("Could not retrieve subscription details ");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Gets the sub.
     * 
     * @return the sub
     * @throws ServiceException
     *             the service exception
     */
    @Transactional
    public List<Subscriptions> getSub() throws ServiceException {
        try {
            LOG.debug("Retrieving all subscriptions");
            return subscriptionDAO.getSub();
        } catch (DAOException e) {
            LOG.error("Could not retrieve retrieve subscription details ");
            throw new ServiceException(e.getExceptionMsg(), e);
        }
    }

    /**
     * Adds the or update subscription.
     * 
     * @param xmlfile
     *            the xmlfile
     * @return true, if successful
     * @throws ServiceException
     *             the service exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException
     *             the parser configuration exception
     * @throws SAXException
     *             the SAX exception
     */
    @Transactional
    public String addOrUpdateSubscription(MultipartFile xmlfile)
            throws ServiceException, IOException, ParserConfigurationException,
            SAXException {
        String fileName = "";
        try {
            byte[] bytes = xmlfile.getBytes();
            String name = xmlfile.getOriginalFilename();

            String ext = name.substring(name.lastIndexOf('.'), name.length());
            if (ext.equals(".xml")) {
                fileName = "" + System.currentTimeMillis() + ext;
                File file = new File("D:/SpringWorkspace", "resources");
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file, "subscriptions");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File temp = new File(file, fileName);

                FileOutputStream fos = new FileOutputStream(temp);
                fos.write(bytes);

                List<Subscriptions> subscriptionList = addSubscriptionFromXML(temp);
                if (!subscriptionList.isEmpty()) {
                    saveOrUpdateSubscription(subscriptionList);
                    fos.close();
                    file.delete();
                    return "Successfuly uploaded "+name;
                } else {
                    fos.close();
                    file.delete();
                    return "Please chose file with correct format.";
                }
            }
            return "Please chose file with correct format.";
        } catch (Exception e) {
            LOG.error("Could not add/update subscription plans");
            throw new ServiceException(
                    "Something went wrong while uploading the file.", e);
        }
    }

    /**
     * Adds the subscription from xml.
     * 
     * @param file
     *            the file
     * @return the list
     * @throws ParserConfigurationException
     *             the parser configuration exception
     * @throws SAXException
     *             the SAX exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public List<Subscriptions> addSubscriptionFromXML(File file)
            throws ServiceException {
        try{
            List<Subscriptions> subscriptionList = new ArrayList<Subscriptions>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("subscription");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Subscriptions subscriptions = new Subscriptions();
                    Element eElement = (Element) nNode;

                    subscriptions.setSubid(Integer.parseInt(eElement
                            .getElementsByTagName("sub_id").item(0)
                            .getTextContent()));
                    subscriptions.setSubName(eElement
                            .getElementsByTagName("sub_name").item(0)
                            .getTextContent());
                    subscriptions.setSubAmount(Integer.parseInt(eElement
                            .getElementsByTagName("sub_amount").item(0)
                            .getTextContent()));
                    subscriptions.setSubDuration(Integer.parseInt(eElement
                            .getElementsByTagName("sub_duration").item(0)
                            .getTextContent()));
                    subscriptions.setMaxBooks(Integer.parseInt(eElement
                            .getElementsByTagName("max_books").item(0)
                            .getTextContent()));
                    subscriptions.setValidity(Integer.parseInt(eElement
                            .getElementsByTagName("validity").item(0)
                            .getTextContent()));

                    subscriptionList.add(subscriptions);
                }
            }
            return subscriptionList;
        }catch(Exception e){
            LOG.error("Could not add/update subscription plans");
            throw new ServiceException(
                    "Something went wrong while uploading the file.", e);
        }
    }

    public boolean saveOrUpdateSubscription(List<Subscriptions> subList) throws ServiceException{
        try{
            for (Subscriptions subscriptions : subList) {
                System.out.println(subscriptions.getSubName());
                List<Subscriptions> list = subscriptionDAO.getValidSub(subscriptions.getSubName());
                if(!list.isEmpty()){
                    System.out.println("updating "+subscriptions.getSubName());
                    subscriptionDAO.updateSubscription(subscriptions.getSubid(), subscriptions);
                } else{
                    System.out.println("saving "+subscriptions.getSubName());
                    subscriptionDAO.saveSubscription(subscriptions);
                    System.out.println("saved");
                }   
            }
            return true;
        }catch(DAOException e){
            LOG.error("Could not add/update subscription plans");
            throw new ServiceException(
                    "Something went wrong while uploading the file.", e);

        }

    }

}
