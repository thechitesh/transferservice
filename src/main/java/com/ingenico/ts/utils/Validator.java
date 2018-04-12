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


    /**
     * Method validate the ID in path param to be numeric
     * @param id - resource id
     * @throws AccountException - application exception
     */
    public void validateId(final String id) throws AccountException {
        if (!StringUtils.isNumeric(id)) {
            throw new AccountException("Invalid Id is supplied", Constants.ID_IN_PATH_PARAM_SHOULD_BE_NUMERIC);
        }

    }

    /**
     * Method used to validate that the accounts involved in transfer should be different.
     * @param transfer - transfere request resource
     * @throws AccountException - application exception
     */
    public void validateTransferObjectAccountNames(final Transfer transfer) throws AccountException {
        if(transfer.getInitiatingAccountName().equalsIgnoreCase(transfer.getCounterPartyAccountName())){
            throw new AccountException("Transfer can not be initiated between same accounts", Constants.TRANSFER_ACCOUNTS_SHOULD_BE_DIFFERENT);
        }
    }


    /**
     * Method validates if the initiating party account has sufficient balance more than amount to transfer,
     * otherwise it will throw exception
     *
     * @param account         - initiating party account
     * @param amountToTranfer - amount to be transferred
     * @throws AccountException - application exception
     */
    public void validateAccountBalanceForTransfer(final Account account, final BigDecimal amountToTranfer) throws AccountException {

        if (account.getBalance().compareTo(amountToTranfer) < 0) {
            throw new AccountException("Balance not sufficient for transfer", Constants.TRANSFER_AMOUNT_EXCEEDS_AVAILABLE_BALANCE);
        }


    }

    /**
     * Validate account for holding balance limit
     * @param account - Account resource
     * @throws AccountException - application exception
     */
    public void validateCunterPartyAccountBalance(final Account account) throws AccountException {
        if(Constants.MAX_LIMIT.compareTo(account.getBalance())<0){
            throw new AccountException("Balance after trasaction exceeds limit of Balance limit of 99999",
                    Constants.BALANCE_AFTER_TRANSACTION_EXCEEDS_ACCOUNT_LIMIT);
        }

    }


}
