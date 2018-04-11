package com.ingenico.ts.controllers;

import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/initiatetransfer")
public class TransferRestService {

    @Autowired
    private AccountService accountService;

    @PutMapping
    public ResponseEntity initiateTransfer(@RequestBody final Transfer transfer) throws AccountException {

        accountService.executeTransfer(transfer);
        return ResponseEntity.ok("DOne");
    }



}
