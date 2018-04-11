package com.ingenico.ts.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class AccountException extends Exception {


    private String errorMessage;

    private String errorCode;


    public AccountException(final String errorMessage){
        super(errorMessage);
    }

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

    public void afterThrowing(DataIntegrityViolationException ex){
        System.out.println("after throwing");
    }
}
