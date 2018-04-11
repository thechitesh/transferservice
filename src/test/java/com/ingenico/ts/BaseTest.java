package com.ingenico.ts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingenico.ts.configuration.TransferServiceApplication;
import com.ingenico.ts.resources.Account;
import com.ingenico.ts.resources.Transfer;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ContextConfiguration(classes = {TransferServiceApplication.class})
@TestExecutionListeners(listeners = {DirtiesContextTestExecutionListener.class})
@Rollback
public class BaseTest extends AbstractJUnit4SpringContextTests {

    private static final String TRANSFER_URI = "/initiatetransfer/v1";

    private static final String ACCOUNTS_URI = "/accounts/v1";


    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext context;

    @Before
    public void beforeClass() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .addFilters(springSecurityFilterChain)
                .build();


    }


    private ResultActions performGetOperation(String uri,
//                                              final HttpHeaders headers,
                                              final String... uriParams) throws Exception {

        uri = uri + "/" + uriParams[0];
        System.out.println("URI " + uri);
        return mockMvc.perform(

                get(uri, (Object[]) uriParams)
//                        .headers(headers)
        );
    }

    private <T> ResultActions performPostOperation(final String uri,
                                                   final T dto,
                                                   final String... uriParams) throws Exception {
        return mockMvc.perform(
                post(uri, (Object[]) uriParams)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );
    }

    protected <T> T extractDtoFromMockResult(final MvcResult mvcResult, final Class<T> clazz) throws IOException {
        String responseBody = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, clazz);
    }

    protected ResultActions getAccountById(final String id) throws Exception {
        return performGetOperation(ACCOUNTS_URI, id);
    }

    protected ResultActions addAccount(Account account) throws Exception {
        return performPostOperation(ACCOUNTS_URI, account);
    }

    protected ResultActions initiateTransfer(Transfer transfer) throws Exception {
        return performPostOperation(TRANSFER_URI, transfer);
    }

}
