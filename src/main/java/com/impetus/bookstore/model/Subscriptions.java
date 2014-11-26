package com.impetus.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Subscriptions.
 */
@Entity
@Table(name = "subscriptions")
public class Subscriptions {

    /** The subid. */
    @Id
    @Column(name = "sub_id")
    private int subid;

    /** The sub name. */
    @Column(name = "sub_name")
    private String subName;

    /** The sub amount. */
    @Column(name = "sub_amount")
    private int subAmount;

    /** The sub duration. */
    @Column(name = "sub_duration")
    private int subDuration;

    /** The max books. */
    @Column(name = "max_books")
    private int maxBooks;

    /** The validity. */
    private int validity;

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
     * Gets the sub name.
     * 
     * @return the sub name
     */
    public String getSubName() {
        return subName;
    }

    /**
     * Sets the sub name.
     * 
     * @param subName
     *            the new sub name
     */
    public void setSubName(String subName) {
        this.subName = subName;
    }

    /**
     * Gets the sub amount.
     * 
     * @return the sub amount
     */
    public int getSubAmount() {
        return subAmount;
    }

    /**
     * Sets the sub amount.
     * 
     * @param subAmount
     *            the new sub amount
     */
    public void setSubAmount(int subAmount) {
        this.subAmount = subAmount;
    }

    /**
     * Gets the sub duration.
     * 
     * @return the sub duration
     */
    public int getSubDuration() {
        return subDuration;
    }

    /**
     * Sets the sub duration.
     * 
     * @param subDuration
     *            the new sub duration
     */
    public void setSubDuration(int subDuration) {
        this.subDuration = subDuration;
    }

    /**
     * Gets the max books.
     * 
     * @return the max books
     */
    public int getMaxBooks() {
        return maxBooks;
    }

    /**
     * Sets the max books.
     * 
     * @param maxBooks
     *            the new max books
     */
    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    /**
     * Checks if is validity.
     * 
     * @return true, if is validity
     */
    public int getValidity() {
        return validity;
    }

    /**
     * Sets the validity.
     * 
     * @param validity
     *            the new validity
     */
    public void setValidity(int validity) {
        this.validity = validity;
    }
}
