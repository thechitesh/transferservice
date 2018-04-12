package com.ingenico.ts.services;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.repository.AccountRepository;
import com.ingenico.ts.utils.Constants;
import com.ingenico.ts.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for validating all the parameters before making CRUD operation
 * @author chitesh
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private Validator validator;


    /**
     * Method used to create an Account in database.
     *
     * @param account - Account resource to be created
     * @return - id of the created resource
     * @throws AccountException - application exception in case of failure
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,
            readOnly = false,rollbackFor = Exception.class)
    public int createAccount(final Account account) throws AccountException {

        try {
            return accountRepository.save(account).getId();
        }catch (DataIntegrityViolationException integrityViolation){
            throw new AccountException("Account name already present", Constants.ACCOUNT_NAME_ALREADY_PRESENT);
        }


    }

    /**
     * Method used to retrieve all the account resorces present in database.
     * @return - list of Accounts
     */
    public List<Account> getAllAccounts(){

        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    /**
     * Method used to retrieve a particular account from database using the account Id.
     * @param id - resource id or account id.
     * @return - Account Resource
     */
    public Account getAccount(final int id) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findOne(id));
        account.orElseThrow(()->new AccountException("Account Not Found",Constants.ACCOUNT_NOT_FOUND));
        return account.get();
    }


    /**
     * Method used to initiate the transfer request from one account to other
     * @param transfer - dto holds the transfer request parameters
     * @throws AccountException - application exception in case of failure
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,
            readOnly = false,rollbackFor = Exception.class)
    public void executeTransfer(Transfer transfer) throws AccountException {

        Optional<Account> initiatingPartyAccount = Optional.ofNullable(accountRepository.findAccountByName(transfer.getInitiatingAccountName()));

        initiatingPartyAccount.orElseThrow(() -> new AccountException("Initiating Party Account Not Found",Constants.INITIATING_PARTY_ACCOUNT_NOT_FOUND));

        validator.validateAccountBalanceForTransfer(initiatingPartyAccount.get(), transfer.getAmount());

        Optional<Account> countPartyAccount = Optional.ofNullable(accountRepository.findAccountByName(transfer.getCounterPartyAccountName()));
        countPartyAccount.orElseThrow(() -> new AccountException("Counter Party Account Not Found",Constants.COUNTER_PARTY_ACCOUNT_NOT_FOUND));
        countPartyAccount.get().setBalance(countPartyAccount.get().getBalance().add(transfer.getAmount()));
        validator.validateCunterPartyAccountBalance(countPartyAccount.get());
        accountRepository.save(countPartyAccount.get());
        initiatingPartyAccount.get().setBalance(initiatingPartyAccount.get().getBalance().subtract(transfer.getAmount()));
        accountRepository.save(initiatingPartyAccount.get());
    }

}
