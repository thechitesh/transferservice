package com.ingenico.ts.controllers;

import com.ingenico.ts.BaseTest;
import com.ingenico.ts.configuration.TransferServiceApplication;
import com.ingenico.ts.exceptions.AccountException;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.ResponseWrapper;
import com.ingenico.ts.resources.Transfer;
import com.ingenico.ts.resources.TransferStatus;
import com.ingenico.ts.services.AccountService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransferRestServiceTest extends BaseTest {


    private final String INITIATING_ACCOUNT = "foo";
    private final String RECEIVING_ACCOUNT = "bar";

    public Transfer transfer;

    @Before
    public void setUp(){

        transfer = new Transfer();
        transfer.setInitiatingAccountName(INITIATING_ACCOUNT);
        transfer.setAmount(BigDecimal.ONE);
        transfer.setCounterPartyAccountName(RECEIVING_ACCOUNT);
    }


    @Test
    public void testAddFirstAccount() throws Exception {
        Account account = new Account();
        account.setName(INITIATING_ACCOUNT);
        account.setBalance(BigDecimal.TEN);

        final ResultActions response = addAccount(account);
        response.andExpect(status().isCreated());
    }

    @Test
    public void testAddSecondAccount() throws Exception {
        Account account = new Account();
        account.setName(RECEIVING_ACCOUNT);
        account.setBalance(BigDecimal.ZERO);

        final ResultActions response = addAccount(account);
        response.andExpect(status().isCreated());
    }

    @Test
    public void testGetAccount() throws Exception {
        ResultActions response = getAccountById("1");




        response.andExpect(status().isOk());
        final Account account = extractDtoFromMockResult(response.andReturn(), Account.class);
        System.out.println("account : "+account.getName());
//        assertEquals(new Integer(1), retResult.getResult().getId());
    }


    @Test
    public void testInitiateTransfer() throws Exception {

        final ResultActions response = initiateTransfer(transfer) ;
        response.andExpect(status().isCreated());
        final ReturnedResult retResult = extractDtoFromMockResult(response.andReturn(), ReturnedResult.class);
        System.out.println("Output : "+retResult.getResponseWrapper().getTransferStatus());
        assertEquals(TransferStatus.EXECUTED, retResult.getResponseWrapper().getTransferStatus());

    }

}
