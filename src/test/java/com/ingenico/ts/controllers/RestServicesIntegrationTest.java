package com.ingenico.ts.controllers;

import com.ingenico.ts.BaseTest;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.resources.TransferStatus;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestServicesIntegrationTest extends BaseTest{



    @Test
    public void test_1addAccount() throws Exception {
        Account account = new Account();
        account.setName(INITIATING_ACCOUNT);
        account.setBalance(BigDecimal.TEN);

        final ResultActions response = addAccount(account);
        response.andExpect(status().isCreated());
        final ReturnedResult retResult = extractDtoFromMockResult(response.andReturn(), ReturnedResult.class);

        assertEquals(new Integer(1), retResult.getResponseWrapper().getId());
    }

    @Test
    public void test_2addAccount() throws Exception {
        Account account = new Account();
        account.setName(RECEIVING_ACCOUNT);
        account.setBalance(BigDecimal.ZERO);

        final ResultActions response = addAccount(account);
        response.andExpect(status().isCreated());
        final ReturnedResult retResult = extractDtoFromMockResult(response.andReturn(), ReturnedResult.class);
        assertEquals(new Integer(2), retResult.getResponseWrapper().getId());
    }



    @Test
    public void test_3InitiateTransfer() throws Exception {

        Transfer transfer = new Transfer();
        transfer.setInitiatingAccountName(INITIATING_ACCOUNT);
        transfer.setAmount(BigDecimal.ONE);
        transfer.setCounterPartyAccountName(RECEIVING_ACCOUNT);

        final ResultActions response = initiateTransfer(transfer) ;
        response.andExpect(status().isOk());
        final ReturnedResult retResult = extractDtoFromMockResult(response.andReturn(), ReturnedResult.class);
        assertEquals(TransferStatus.EXECUTED, retResult.getResponseWrapper().getTransferStatus());

    }


    @Test
    public void test_4AccountBalanceInFirstAccount() throws Exception {

        ResultActions response = getAccountById("1");
        response.andExpect(status().isOk());
        final Account account = extractDtoFromMockResult(response.andReturn(), Account.class);
        assertTrue(new BigDecimal(9).compareTo(account.getBalance())==0);
    }



    @Test
    public void test_5AccountBalanceInSecondAccount() throws Exception {

        ResultActions response = getAccountById("2");
        response.andExpect(status().isOk());
        final Account account = extractDtoFromMockResult(response.andReturn(), Account.class);
        assertTrue(new BigDecimal(1).compareTo(account.getBalance())==0);
    }


}
