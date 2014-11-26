package com.impetus.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRoles.
 */
@Entity
@Table(name = "user_roles")
public class UserRoles {

    /** The iduser roles. */
    @Id
    @Column(name = "iduser_roles")
    private int iduserRoles;

    /** The username. */
    private String username;

    /** The role. */
    private String role;

    /**
     * Gets the iduser roles.
     * 
     * @return the iduser roles
     */
    public int getIduserRoles() {
        return iduserRoles;
    }

    /**
     * Sets the iduser roles.
     * 
     * @param iduserRoles
     *            the new iduser roles
     */
    public void setIduserRoles(int iduserRoles) {
        this.iduserRoles = iduserRoles;
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
     * Gets the role.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role.
     * 
     * @param role
     *            the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

}
