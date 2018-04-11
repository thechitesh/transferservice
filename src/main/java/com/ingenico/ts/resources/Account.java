package com.ingenico.ts.resources;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity

public class Account {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull(message = "Balance can not be null")
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
