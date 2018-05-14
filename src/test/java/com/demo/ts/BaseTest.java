package com.demo.ts;

import com.demo.ts.configuration.TransferServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.ts.resources.Account;
import com.demo.ts.resources.Transfer;
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
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

    private static final String TRANSFER_URI = "/v1/initiatetransfer";

    private static final String ACCOUNTS_URI = "/v1/accounts";


    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext context;


    public final String INITIATING_ACCOUNT = "foo";
    public final String RECEIVING_ACCOUNT = "bar";


    @Before
    public void beforeClass() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();

    }


    private ResultActions performGetOperation(String uri,
                                              final String uriParams) throws Exception {

        return mockMvc.perform(
                get( uri + "/" + uriParams)
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
