package com.ingenico.ts.utils;

import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    @InjectMocks
    private Validator validator;


    @Test
    public void test_validateId(){
     Stream.of("1.1","adfdfad","1@#").forEach( i -> {
                try {
                    validator.validateId(i);
                } catch (Exception e) {
                    Assert.assertTrue(e instanceof AccountException);
                    Assert.assertEquals(Constants.ID_IN_PATH_PARAM_SHOULD_BE_NUMERIC,((AccountException)e).getErrorCode());
                }
            });

    }

    @Test
    public void test_validateTransferObjAccNames(){

        Transfer transfer = new Transfer();
        transfer.setInitiatingAccountName("FOO");
        transfer.setCounterPartyAccountName("FOO");
        try {
            validator.validateTransferObjectAccountNames(transfer);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.TRANSFER_ACCOUNTS_SHOULD_BE_DIFFERENT,((AccountException)e).getErrorCode());
        }

    }

    @Test
    public void test_validateAccountBalanceForTransfer(){

        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        try {
            validator.validateAccountBalanceForTransfer(account,BigDecimal.TEN);
        }  catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.TRANSFER_AMOUNT_EXCEEDS_AVAILABLE_BALANCE,((AccountException)e).getErrorCode());
        }
    }

    @Test
    public void test_validateAccountBalanceFor_SameTransferAmount(){

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        try {
            validator.validateAccountBalanceForTransfer(account,BigDecimal.TEN);
        }  catch (Exception e) {
            Assert.assertTrue(e instanceof AccountException);
            Assert.assertEquals(Constants.TRANSFER_AMOUNT_EXCEEDS_AVAILABLE_BALANCE,((AccountException)e).getErrorCode());
        }
    }

}
