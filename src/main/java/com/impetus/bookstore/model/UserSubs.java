package com.impetus.bookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSubs.
 */
@Entity
@Table(name = "usersubs")
public class UserSubs {

    /** The usersubid. */
    @Id
    @Column(name = "usersub_id")
    private int usersubid;

    /** The username. */
    private String username;

    /** The subid. */
    @Column(name = "sub_id")
    private int subid;

    /** The start date. */
    @Column(name = "start_date")
    private Date startDate;

    /** The end date. */
    @Column(name = "end_date")
    private Date endDate;

    /** The days. */
    private int days;

    /** The books left. */
    @Column(name = "books_left")
    private int booksLeft;

    /** The valid. */
    private boolean valid;

    /**
     * Gets the usersubid.
     * 
     * @return the usersubid
     */
    public int getUsersubid() {
        return usersubid;
    }

    /**
     * Sets the usersubid.
     * 
     * @param usersubid
     *            the new usersubid
     */
    public void setUsersubid(int usersubid) {
        this.usersubid = usersubid;
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
     * Gets the subid.
     * 
     * @return the subid
     */
    public int getSubid() {
        return subid;
    }

    /**
     * Sets the subid.
     * 
     * @param subid
     *            the new subid
     */
    public void setSubid(int subid) {
        this.subid = subid;
    }

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate
     *            the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the books left.
     * 
     * @return the books left
     */
    public int getBooksLeft() {
        return booksLeft;
    }

    /**
     * Sets the books left.
     * 
     * @param booksLeft
     *            the new books left
     */
    public void setBooksLeft(int booksLeft) {
        this.booksLeft = booksLeft;
    }

    /**
     * Checks if is valid.
     * 
     * @return true, if is valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the valid.
     * 
     * @param valid
     *            the new valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Gets the days.
     * 
     * @return the days
     */
    public int getDays() {
        return days;
    }

    /**
     * Sets the days.
     * 
     * @param days
     *            the new days
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     * 
     * @param cal
     *            the new end date
     */
    public void setEndDate(Date cal) {
        this.endDate = cal;
    }

}