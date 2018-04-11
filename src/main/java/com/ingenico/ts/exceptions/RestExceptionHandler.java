package com.ingenico.ts.exceptions;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public ResponseEntity<String> handleAccessDeniedException(final AccountException e) {

//        LOGGER.info("HTTP 400 Bad Request", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleInvalidMethodArgumentException(final MethodArgumentNotValidException e) {
        System.out.println("here in method argument advice");
        final String details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage() + " : " + x.getRejectedValue())
                .collect(Collectors.joining("; "));
//        LOGGER.info("HTTP 400 Bad Request", e);
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<String> handleConstraintException(final ConstraintViolationException e) {
        System.out.println("here in ConstraintViolationException advice");
        final String str = e.getConstraintViolations().stream().map(m ->m.getMessage()).collect(Collectors.joining(";"));

        return new ResponseEntity<>(str, HttpStatus.BAD_REQUEST);
    }
}
