package com.demo.ts.resources;

import com.demo.ts.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * Resource model for initiating a transfer from one account to other
 * @author chitesh
 */
public class Transfer {

    @NotEmpty(message = Constants.INITIATING_PARTY_ACCOUNT_SHOULD_BE_FILLED)
    private String initiatingAccountName;

    @Range(min=0, max=99999,message = Constants.TRANSFER_AMOUNT_SHOULD_BE_GREATE_THAN_ZERO)
    private BigDecimal amount;

    @NotEmpty(message = Constants.COUNTER_PARTY_ACCOUNT_SHOULD_BE_FILLED)
    private String counterPartyAccountName;

    public String getInitiatingAccountName() {
        return initiatingAccountName;
    }

    public void setInitiatingAccountName(String initiatingAccountName) {
        this.initiatingAccountName = initiatingAccountName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCounterPartyAccountName() {
        return counterPartyAccountName;
    }

    public void setCounterPartyAccountName(String counterPartyAccountName) {
        this.counterPartyAccountName = counterPartyAccountName;
    }
}
