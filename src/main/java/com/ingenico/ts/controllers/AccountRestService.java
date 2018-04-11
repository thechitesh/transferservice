package com.ingenico.ts.controllers;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountRestService {

    @Autowired
    private AccountService accountService;

    public ResponseEntity getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getAccount(@PathVariable("id") final String id){
        return ResponseEntity.ok(accountService.getAccount(Integer.parseInt(id)));
    }

    @PostMapping
    public ResponseEntity createAcocunt(@RequestBody Account account) throws AccountException {
        System.out.println("Account "+account.getName());

        return ResponseEntity.ok(accountService.createAccount(account));
    }




}
