package com.ingenico.ts.controllers;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.ResponseWrapper;
import com.ingenico.ts.services.AccountService;
import com.ingenico.ts.utils.Validator;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Class exposes Restful web service for Accounts resource.
 * It provides creation of the account and getting details of existing account in database.
 *
 * @author chitesh
 */
@RestController
@RequestMapping("/v1/accounts")
public class AccountRestService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Validator validator;



    /**
     * Method provides list of all available account
     * @return - list of accounts
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Method provides details about a particular account
     * @param id - identifier of the account resource
     * @return -Account resource
     * @throws AccountException - application exception
     */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccountById(@PathVariable("id") final String id) throws AccountException {
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAcocunt(@RequestBody @Valid  Account account) throws AccountException {

        final int id =accountService.createAccount(account);
        ResponseWrapper responseWrapper  = new ResponseWrapper();
        responseWrapper.setId(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(responseWrapper);
    }




}
