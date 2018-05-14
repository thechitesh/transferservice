package com.demo.ts.exceptions;

/**
 * Applicaiton Exception for Transfer Service Application
 *
 * @author chitesh
 */
public class AccountException extends Exception {


    private String errorMessage;

    private String errorCode;


    /**
     * Parametrized Constructor
     * @param errorMessage - error message text
     */
    public AccountException(final String errorMessage){
        super(errorMessage);
    }

    /**
     * Parametrized Constructor
     * @param errorMessage - error message text
     * @param errorCode - error message code
     */
    public AccountException(final String errorMessage, final String errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
