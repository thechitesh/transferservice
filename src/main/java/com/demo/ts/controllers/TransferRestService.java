package com.demo.ts.controllers;

import com.demo.ts.resources.ResponseWrapper;
import com.demo.ts.resources.Transfer;
import com.demo.ts.exceptions.AccountException;
import com.demo.ts.resources.TransferStatus;
import com.demo.ts.services.AccountService;
import com.demo.ts.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest Service Controller for the Initiating Transfer from one Account to Other
 *
 * @author chitesh
 */
@RestController
@RequestMapping("/v1/initiatetransfer")
public class TransferRestService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private Validator validator;

    /**
     * Method takes the valid transfer request resource and forwards it to the servie for execution
     * @param transfer - transfer request resource
     * @return - execution response
     * @throws AccountException - application exception
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity initiateTransfer(@RequestBody @Valid Transfer transfer) throws AccountException {

        validator.validateTransferObjectAccountNames(transfer);
        accountService.executeTransfer(transfer);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setTransferStatus(TransferStatus.EXECUTED);
        return ResponseEntity.ok(responseWrapper);
    }



}
