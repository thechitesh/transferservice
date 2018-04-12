package com.ingenico.ts.exceptions;

import com.ingenico.ts.utils.Constants;
import com.ingenico.ts.utils.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Exception Handler class which maps different exception with proper HTTP Status Code
 *
 * @author chitesh
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private Error error;

    static Map<String,String> errorMap;

    /**
     * loading static map with error codes and error messages
     */
    static{
       errorMap = new HashMap<>();
        errorMap.put(Constants.TRANSFER_AMOUNT_SHOULD_BE_GREATE_THAN_ZERO,"Transfer Amount should be in a range of 0 to 99999");
        errorMap.put(Constants.INITIATING_PARTY_ACCOUNT_SHOULD_BE_FILLED,"Initiating Party Account Name can not empty");
        errorMap.put(Constants.COUNTER_PARTY_ACCOUNT_SHOULD_BE_FILLED,"Counter Party Account Name can not be empty");
        errorMap.put(Constants.ACCOUNT_NAME_SHOULD_BE_FILLED,"Account Name can not empty");
        errorMap.put(Constants.BALANCE_NOT_NULL,"Balance can not be null");
        errorMap.put(Constants.BALANCE_SHOULE_SHOULD_BE_POSITIVE,"Balance should be a in a range of 0 to 99999");


    }


    /**
     * Method handles application exception thrown by different methods
     * @param e - application exception
     * @return - ResponseEntity for the user
     */
    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public ResponseEntity<com.ingenico.ts.utils.Error> handleApplictionException(final AccountException e) {

        error.setMessage(e.getErrorMessage());
        error.setCode(e.getErrorCode());

        if(e.getErrorCode().equals(Constants.ACCOUNT_NOT_FOUND) || e.getErrorCode().equalsIgnoreCase(Constants.INITIATING_PARTY_ACCOUNT_NOT_FOUND
        )||e.getErrorCode().equalsIgnoreCase(Constants.COUNTER_PARTY_ACCOUNT_NOT_FOUND)){
            LOGGER.info("HTTP 404 Resource Not found", e);
            return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
        }
        else if(e.getErrorCode().equals(Constants.TRANSFER_AMOUNT_EXCEEDS_AVAILABLE_BALANCE)){
            LOGGER.info("HTTP 403 request is forbidden due to insufficient balance", e);
            return new ResponseEntity<Error>(error, HttpStatus.FORBIDDEN);
        }
        else if(e.getErrorCode().equals(Constants.ACCOUNT_NAME_ALREADY_PRESENT)){
            LOGGER.info("HTTP 409 with the name, already present", e);
            return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
        }
        else if(e.getErrorCode().equals(Constants.BALANCE_AFTER_TRANSACTION_EXCEEDS_ACCOUNT_LIMIT)){
            LOGGER.info("HTTP 403 request is forbidden as account overshoot balance after trx, max limit allowed : 99999", e);
            return new ResponseEntity<com.ingenico.ts.utils.Error>(error, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<com.ingenico.ts.utils.Error>(error, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method handle exception thrown during bean validation
     * @param e - instance of MethodArgumentNotValidException
     * @return - ResponseEntity for the user
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Error> handleInvalidMethodArgumentException(final MethodArgumentNotValidException e) {
        final Optional<Error> err = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> {
                    error.setCode(x.getDefaultMessage());
                    error.setMessage(errorMap.get(x.getDefaultMessage()));
                    return error;
                }).findAny();
        LOGGER.info("HTTP 400 Bad Request", e);
        return new ResponseEntity<Error>(err.get(), HttpStatus.BAD_REQUEST);
    }


    /**
     * Method handles constraintViolationException thrown
     * @param e - instance of ConstranintViolationException
     * @return - ResponseEntity for the user
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Error> handleConstraintException(final ConstraintViolationException e) {

        final String str = e.getConstraintViolations().stream().map(m ->m.getMessage() +":"+m.getPropertyPath()).collect(Collectors.joining(";"));
        error.setMessage(str);
        LOGGER.info("HTTP 400 Bad Request", e);
        return new ResponseEntity<com.ingenico.ts.utils.Error>(error, HttpStatus.BAD_REQUEST);
    }
}
