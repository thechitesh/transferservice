package com.ingenico.ts.resources;

import com.ingenico.ts.utils.Constants;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Resource model for initiating a transfer from one account to other
 * @author chitesh
 */
public class Transfer {

    @NotNull(message = Constants.INITIATING_PARTY_ACCOUNT_SHOULD_BE_FILLED)
    private String initiatingAccountName;

    @Range(min=0, max=99999,message = Constants.TRANSFER_AMOUNT_SHOULD_BE_GREATE_THAN_ZERO)
    private BigDecimal amount;

    @NotNull(message = "Counter Party Account Name can not be empty")
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
