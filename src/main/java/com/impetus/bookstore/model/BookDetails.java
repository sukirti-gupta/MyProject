package com.impetus.bookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class BookDetails.
 */
@Entity
@Table(name = "bookdetails")
public class BookDetails {

    /** The bookid. */
    @Id
    @Column(name = "book_id")
    private int bookid;

    /** The name. */
    private String name;

    /** The author. */
    private String author;

    /** The publisher. */
    private String publisher;

    /** The category. */
    private String category;

    /** The language. */
    private String language;

    /** The description. */
    private String description;

    /** The image. */
    private String image;

    /** The avail. */
    private int avail;

    /** The date added. */
    @Column(name = "date_added")
    private Date dateAdded;

    /** The exist. */
    private String exist;

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
     * Gets the author.
     * 
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     * 
     * @param author
     *            the new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the publisher.
     * 
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher.
     * 
     * @param publisher
     *            the new publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the category.
     * 
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category.
     * 
     * @param category
     *            the new category
     */
    public void setCategory(String category) {
        this.category = category;
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
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the image.
     * 
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image.
     * 
     * @param image
     *            the new image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the avail.
     * 
     * @return the avail
     */
    public int getAvail() {
        return avail;
    }

    /**
     * Sets the avail.
     * 
     * @param avail
     *            the new avail
     */
    public void setAvail(int avail) {
        this.avail = avail;
    }

    /**
     * Gets the date added.
     * 
     * @return the date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date added.
     * 
     * @param dateAdded
     *            the new date added
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the exist.
     * 
     * @return the exist
     */
    public String getExist() {
        return exist;
    }

    /**
     * Sets the exist.
     * 
     * @param exist
     *            the new exist
     */
    public void setExist(String exist) {
        this.exist = exist;
    }

}