package com.ingenico.ts.controllers;

import com.ingenico.ts.resources.ResponseWrapper;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.TransferStatus;
import com.ingenico.ts.services.AccountService;
import com.ingenico.ts.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest Service Controller for the Initiating Transfer from one Account to Other
 *
 * @author chitesh
 */
@RestController
@RequestMapping("/initiatetransfer")
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
    @PostMapping(value = "/v1")
    public ResponseEntity initiateTransfer(@RequestBody @Valid Transfer transfer) throws AccountException {

        validator.validateTransferObjectAccountNames(transfer);
        accountService.executeTransfer(transfer);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setTransferStatus(TransferStatus.EXECUTED);
        return ResponseEntity.ok(responseWrapper);
    }



}
