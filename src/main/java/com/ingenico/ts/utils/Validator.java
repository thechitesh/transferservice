package com.ingenico.ts.utils;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.exceptions.AccountException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class Validator {


    public void validateAccountBalanceForTransfer(Optional<Account> account , BigDecimal amountToTranfer) throws AccountException {

        if(account.get().getBalance().compareTo(BigDecimal.ZERO) < 1){
            throw new AccountException("Insufficient balance in Account");
        }
        if(account.get().getBalance().compareTo(amountToTranfer) < 0 ){
            throw new AccountException("Request transfer amount is more thant the available balance");
        }
    }


}
