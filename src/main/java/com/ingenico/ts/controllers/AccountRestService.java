package com.ingenico.ts.controllers;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.ResponseWrapper;
import com.ingenico.ts.services.AccountService;
import com.ingenico.ts.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Class exposes Restful web service for Accounts resource.
 * It provides creation of the account and getting details of exisiting account in database.
 */
@RestController
@RequestMapping("/accounts/v1")
public class AccountRestService {

    private AccountService accountService;
    private Validator validator;

    @Autowired
    private AccountRestService(final AccountService accountService, final Validator validator){
        this.accountService = accountService;
        this.validator = validator;
    }


    /**
     * Method provides list of all available account
     * @return - list of accounts
     */
    @GetMapping
    public ResponseEntity getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Method provides details about a particular account
     * @param id - identifier of the account resource
     * @return -Account resource
     * @throws AccountException - application exception
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getAccount(@PathVariable("id") final String id) throws AccountException {
        validator.validateId(id);
        return ResponseEntity.ok(accountService.getAccount(Integer.parseInt(id)));
    }

    /**
     * Method helps in creating a Account resource. It takes a valid Account resource and forwards
     * it to the service for further actions.
     * @param account - Account resource intended for creation
     * @return - id of the created resource
     * @throws AccountException - application exception
     */
    @PostMapping
    public ResponseEntity createAcocunt(@RequestBody @Valid  Account account) throws AccountException {
        final int id =accountService.createAccount(account);
        ResponseWrapper responseWrapper  = new ResponseWrapper();
        responseWrapper.setId(id);
        return ResponseEntity.created(URI.create("/"+id)).body(responseWrapper);
    }




}
