package com.ingenico.ts.services;

import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.repository.AccountRepository;
import com.ingenico.ts.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Validator validator;


    public int createAccount(final Account account) throws AccountException {

        try {
            return accountRepository.save(account).getId();
        }catch (DataIntegrityViolationException integrityViolation){

            throw new AccountException("Not Account Unique name");
        }

    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    public Account getAccount(final int id){
        return accountRepository.findOne(id);
    }


    public void executeTransfer(Transfer transfer) throws AccountException {

        Optional<Account> initiatingPartyAccount = Optional.ofNullable(accountRepository.findAccountByName(transfer.getInitiatingAccountName()));

        validator.validateAccountBalanceForTransfer(initiatingPartyAccount, transfer.getAmount());

        Account countPartyAccount = accountRepository.findAccountByName(transfer.getCounterPartyAccountName());
        System.out.println("initial amount "+countPartyAccount.getBalance());
        countPartyAccount.setBalance(countPartyAccount.getBalance().add(transfer.getAmount()));
        System.out.println("update balance : "+countPartyAccount.getBalance());
        accountRepository.save(countPartyAccount);

        initiatingPartyAccount.get().setBalance(initiatingPartyAccount.get().getBalance().subtract(transfer.getAmount()));
        accountRepository.save(initiatingPartyAccount.get());




    }

}
