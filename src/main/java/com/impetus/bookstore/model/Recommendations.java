package com.impetus.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Recommendations.
 */
@Entity
@Table(name = "recommendations")
public class Recommendations {

    /** The id. */
    @Id
    private int id;

    /** The username. */
    private String username;

    /** The recommendations. */
    private String recommendations;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(int id) {
        this.id = id;
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
