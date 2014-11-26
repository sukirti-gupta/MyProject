package com.impetus.bookstore.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceException.
 */
public class ServiceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1021432697495175430L;

    /** The exception msg. */
    private String exceptionMsg;

    /** The cause. */
    private Throwable cause;

    /** The e. */
    private Exception e;

    /**
     * Instantiates a new service exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param e
     *            the e
     */
    public ServiceException(String exceptionMsg, Exception e) {
        this.exceptionMsg = exceptionMsg;
        this.e = e;
    }

    /**
     * Instantiates a new service exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     * @param cause
     *            the cause
     */
    public ServiceException(String exceptionMsg, Throwable cause) {
        this.exceptionMsg = exceptionMsg;
        this.cause = cause;
    }

    /**
     * Instantiates a new service exception.
     * 
     * @param exceptionMsg
     *            the exception msg
     */
    public ServiceException(String exceptionMsg) {
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
     *            the new exception msg
     */
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
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
     * Sets the exception msg.
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
}
