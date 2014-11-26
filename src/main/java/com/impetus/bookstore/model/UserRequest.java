package com.impetus.bookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRequest.
 */
@Entity
@Table(name = "userrequest")
public class UserRequest {

    /** The reqid. */
    @Id
    @GeneratedValue
    @Column(name = "req_id")
    private long reqid;

    /** The username. */
    private String username;

    /** The bookid. */
    @Column(name = "book_id")
    private int bookid;

    /** The req type. */
    @Column(name = "req_type")
    private String reqType;

    /** The start date. */
    @Column(name = "start_date")
    private Date startDate;

    /** The end date. */
    @Column(name = "end_date")
    private Date endDate;

    /** The req status. */
    @Column(name = "req_status")
    private String reqStatus;

    /** The req address. */
    @Column(name = "req_address")
    private String reqAddress;

    /**
     * Gets the reqid.
     * 
     * @return the reqid
     */
    public long getReqid() {
        return reqid;
    }

    /**
     * Sets the reqid.
     * 
     * @param reqid
     *            the new reqid
     */
    public void setReqid(long reqid) {
        this.reqid = reqid;
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
     * Gets the bookid.
     * 
     * @return the bookid
     */
    public int getBookid() {
        return bookid;
    }

    /**
     * Sets the bookid.
     * 
     * @param bookid
     *            the new bookid
     */
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    /**
     * Gets the req type.
     * 
     * @return the req type
     */
    public String getReqType() {
        return reqType;
    }

    /**
     * Sets the req type.
     * 
     * @param reqType
     *            the new req type
     */
    public void setReqType(String reqType) {
        this.reqType = reqType;
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
     * Gets the req status.
     * 
     * @return the req status
     */
    public String getReqStatus() {
        return reqStatus;
    }

    /**
     * Sets the req status.
     * 
     * @param reqStatus
     *            the new req status
     */
    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    /**
     * Gets the req address.
     * 
     * @return the req address
     */
    public String getReqAddress() {
        return reqAddress;
    }

    /**
     * Sets the req address.
     * 
     * @param reqAddress
     *            the new req address
     */
    public void setReqAddress(String reqAddress) {
        this.reqAddress = reqAddress;
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
     * @param endDate
     *            the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
