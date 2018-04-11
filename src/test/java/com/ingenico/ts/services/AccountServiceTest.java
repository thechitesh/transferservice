package com.ingenico.ts.services;

import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.repository.AccountRepository;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.utils.Constants;
import com.ingenico.ts.utils.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {


    @InjectMocks
    private AccountService accountService;

    @Mock
    private Validator validator;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private Account account;

    private final String ACCOUNT_NAME = "FOO";
    private final String RECEIVING_ACCOUNT_NAME = "BAR";

    @InjectMocks
    private Transfer transfer;

    @Before
    public void setUp(){
        account.setId(1);
        account.setName(ACCOUNT_NAME);
        account.setBalance(BigDecimal.TEN);

        transfer.setInitiatingAccountName(ACCOUNT_NAME);
        transfer.setCounterPartyAccountName(RECEIVING_ACCOUNT_NAME);
        transfer.setAmount(BigDecimal.ONE);
    }

    @Test
    public void test_createAccount_Exception(){
        Mockito.when(accountRepository.save(account)).thenThrow(DataIntegrityViolationException.class);
        try {
            accountService.createAccount(account);
        } catch (Exception e) {

            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.ACCOUNT_NAME_ALREADY_PRESENT, ((AccountException)e).getErrorCode());
        }

    }

    @Test
    public void test_getAllAccount(){
        Account account1 = new Account();
        account1.setName("Bar");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account) ;
        accounts.add(account1);

        Mockito.when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> outAccoutList = accountService.getAllAccounts();
        Assert.assertEquals(accounts.size(),outAccoutList.size());
    }


    @Test
    public void test_getAccountById() throws AccountException {

        Mockito.when(accountRepository.findOne(1)).thenReturn(account);
        Account outAccount = accountService.getAccount(1);

        Assert.assertEquals(ACCOUNT_NAME,outAccount.getName());
        Assert.assertEquals(account,outAccount);

    }

    @Test
    public void test_getAccountById_Exception(){
        Mockito.when(accountRepository.findOne(1)).thenReturn(null);
        try {
            Account outAccount = accountService.getAccount(1);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.ACCOUNT_NOT_FOUND,((AccountException)e).getErrorCode());
        }
    }

    @Test
    public void test_executeTransfer_InitiatingAccountNotFound_Exp(){

        Mockito.when(accountRepository.findAccountByName(ACCOUNT_NAME)).thenReturn(null);
        try {
            accountService.executeTransfer(transfer);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.INITIATING_PARTY_ACCOUNT_NOT_FOUND,((AccountException)e).getErrorCode());
        }
    }

    @Test
    public void test_executeTransfer_ReceivingAccountNotFound_Exp(){

        Mockito.when(accountRepository.findAccountByName(ACCOUNT_NAME)).thenReturn(account);
        Mockito.when(accountRepository.findAccountByName(RECEIVING_ACCOUNT_NAME)).thenReturn(null);

        try {
            accountService.executeTransfer(transfer);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.COUNTER_PARTY_ACCOUNT_NOT_FOUND,((AccountException)e).getErrorCode());
        }
    }

}
