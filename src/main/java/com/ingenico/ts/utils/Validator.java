package com.ingenico.ts.utils;

import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.Account;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Validation class used to validate the parameter
 *
 * @author chitesh
 */

@Component
public class Validator {


    public void validateId(String id) throws AccountException {
        System.out.println("Here");
        if (!StringUtils.isNumeric(id)) {
            throw new AccountException("Invalid Id is supplied", Constants.ID_IN_PATH_PARAM_SHOULD_BE_NUMERIC);
        }

    }

    /**
     * Method validates if the initiating party account has sufficient balance more than amount to transfer,
     * otherwise it will throw exception
     *
     * @param account         - inititing party account
     * @param amountToTranfer - amount to be transferred
     * @throws AccountException - application exception
     */
    public void validateAccountBalanceForTransfer(Optional<Account> account, BigDecimal amountToTranfer) throws AccountException {

        if (account.get().getBalance().compareTo(amountToTranfer) < 0) {
            throw new AccountException("Request transfer amount is more thant the available balance", Constants.TRANSFER_AMOUNT_EXCEEDS_AVAILABLE_BALANCE);
        }

    }


}
