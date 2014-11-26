package com.impetus.bookstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class NewAndPopular.
 */
@Entity
@Table(name = "newandpopular")
public class NewAndPopular {

    /** The date. */
    @Id
    /** The date. */
    private Date date;

    /** The newbooks. */
    private String newbooks;

    /** The popular. */
    private String popular;

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     * 
     * @param date
     *            the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the newbooks.
     * 
     * @return the newbooks
     */
    public String getNewbooks() {
        return newbooks;
    }

    /**
     * Sets the newbooks.
     * 
     * @param newbooks
     *            the new newbooks
     */
    public void setNewbooks(String newbooks) {
        this.newbooks = newbooks;
    }

    /**
     * Gets the popular.
     * 
     * @return the popular
     */
    public String getPopular() {
        return popular;
    }

    /**
     * Sets the popular.
     * 
     * @param popular
     *            the new popular
     */
    public void setPopular(String popular) {
        this.popular = popular;
    }
}
