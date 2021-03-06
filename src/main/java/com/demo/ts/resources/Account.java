package com.demo.ts.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.demo.ts.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Account Resource for the Transfer Service Application
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
public class Account {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    @NotEmpty(message = Constants.ACCOUNT_NAME_SHOULD_BE_FILLED)
    private String name;

    @NotNull(message = Constants.BALANCE_NOT_NULL)
    @Range(min=0, max=99999,message = Constants.BALANCE_SHOULE_SHOULD_BE_POSITIVE)
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
