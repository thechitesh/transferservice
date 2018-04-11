package com.ingenico.ts.resources;

import java.math.BigDecimal;

public class Transfer {

    private String initiatingAccountName;
    private BigDecimal amount;
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
