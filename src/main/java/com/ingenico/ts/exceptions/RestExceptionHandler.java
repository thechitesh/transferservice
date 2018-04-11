package com.ingenico.ts.exceptions;

import com.ingenico.ts.utils.Constants;
import com.ingenico.ts.utils.Error;
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

    static Map<String,String> errorMap;

    /**
     * loading static map with error codes and error messages
     */
    static{
       errorMap = new HashMap<>();
        errorMap.put(Constants.TRANSFER_AMOUNT_SHOULD_BE_GREATE_THAN_ZERO,"Transfer Amount should be greater than zero");
        errorMap.put(Constants.INITIATING_PARTY_ACCOUNT_SHOULD_BE_FILLED,"Initiating Party Account Name can not empty");
        errorMap.put(Constants.COUNTER_PARTY_ACCOUNT_SHOULD_BE_FILLED,"Counter Party Account Name can not be empty");
        errorMap.put(Constants.ACCOUNT_NAME_SHOULD_BE_FILLED,"Account Name can not empty");
        errorMap.put(Constants.BALANCE_NOT_NULL,"Balance can not be null");
        errorMap.put(Constants.BALANCE_SHOULE_SHOULD_BE_POSITIVE,"Balance should be a positive number");

    }


    /**
     * Method handles application exception thrown by different methods
     * @param e - application exception
     * @return - ResponseEntity for the user
     */
    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public ResponseEntity<com.ingenico.ts.utils.Error> handleApplictionException(final AccountException e) {

        com.ingenico.ts.utils.Error err = new Error();
        err.setMessage(e.getErrorMessage());
        err.setCode(e.getErrorCode());
        return new ResponseEntity<com.ingenico.ts.utils.Error>(err, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method handle exception thrown during bean validation
     * @param e - instance of MethodArgumentNotValidException
     * @return - ResponseEntity for the user
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Error> handleInvalidMethodArgumentException(final MethodArgumentNotValidException e) {

        Error err = new Error();
        final Optional<Error> details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> {
                   err.setCode(x.getDefaultMessage());
                   err.setMessage(errorMap.get(x.getDefaultMessage()));
                    return err;
                }).findAny();
//        LOGGER.info("HTTP 400 Bad Request", e);

        return new ResponseEntity<Error>(details.get(), HttpStatus.BAD_REQUEST);
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
        com.ingenico.ts.utils.Error err = new Error();
        err.setMessage(str);
        return new ResponseEntity<com.ingenico.ts.utils.Error>(err, HttpStatus.BAD_REQUEST);
    }
}
