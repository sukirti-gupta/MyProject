package com.impetus.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDetails.
 */
@Entity
@Table(name = "userdetails")
public class UserDetails {

    /** The userid. */
    @Id
    @Column(name = "user_id")
    private int userid;

    /** The name. */
    private String name;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The eid. */
    private String eid;

    /** The address. */
    private String address;

    /** The contact. */
    private String contact;

    /** The language. */
    private String language;

    /** The recommendations. */
    private String recommendations;

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the eid.
     * 
     * @return the eid
     */
    public String getEid() {
        return eid;
    }

    /**
     * Sets the eid.
     * 
     * @param eid
     *            the new eid
     */
    public void setEid(String eid) {
        this.eid = eid;
    }

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address
     *            the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the contact.
     * 
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     * 
     * @param contact
     *            the new contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username
     *            the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the userid.
     * 
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * 
     * @param language
     *            the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the recommendations.
     * 
     * @return the recommendations
     */
    public String getRecommendations() {
        return recommendations;
    }

    /**
     * Sets the recommendations.
     * 
     * @param recommendations
     *            the new recommendations
     */
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
