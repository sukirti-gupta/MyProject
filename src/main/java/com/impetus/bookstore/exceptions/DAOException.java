package com.impetus.bookstore.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOException.
 */
public class DAOException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8535141307933996019L;

    /** The exception msg. */
    private String exceptionMsg;

    /** The cause. */
    private Throwable cause;

    /** The e. */
    private Exception e;

    /**
     * Instantiates a new DAO exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param e
     *            the e
     */
    public DAOException(String exceptionMsg, Exception e) {
        this.exceptionMsg = exceptionMsg;
        this.e = e;
    }

    /**
     * Instantiates a new DAO exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param cause
     *            the cause
     */
    public DAOException(String exceptionMsg, Throwable cause) {
        this.exceptionMsg = exceptionMsg;
        this.cause = cause;
    }

    /**
     * Instantiates a new DAO exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     */
    public DAOException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     * Gets the exception msg.
     * 
     * @return the exception msg
     */
    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    /**
     * Gets the exception cause.
     * 
     * @return the exception cause
     */
    public Throwable getExceptionCause() {
        return this.cause;
    }

    /**
     * Gets the exception.
     * 
     * @return the exception
     */
    public Exception getException() {
        return this.e;
    }

    /**
     * Sets the exception msg.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param cause
     *            the cause
     */
    public void setExceptionMsg(String exceptionMsg, Throwable cause) {
        this.exceptionMsg = exceptionMsg;
        this.cause = cause;
    }

    /**
     * Sets the exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param e
     *            the e
     */
    public void setExceptionMsg(String exceptionMsg, Exception e) {
        this.exceptionMsg = exceptionMsg;
        this.e = e;
    }

    /**
     * Sets the exception msg.
     * 
     * @param exceptionMsg
     *            the new exception msg
     */
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

}
