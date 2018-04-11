package com.ingenico.ts.utils;

import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
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
     * Method used to validate that the accounts involved in transfer should be different.
     * @param transfer - transfere request resource
     * @throws AccountException - application exception
     */
    public void validateTransferObject(final Transfer transfer) throws AccountException {
        if(transfer.getInitiatingAccountName().equalsIgnoreCase(transfer.getCounterPartyAccountName())){
            throw new AccountException("Transfer can be initiated between different accounts", Constants.TRANSFER_ACCOUNTS_SHOULD_BE_DIFFERENT);
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
