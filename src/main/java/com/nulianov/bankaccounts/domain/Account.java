package com.nulianov.bankaccounts.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private BigDecimal balance;

    public Account(String id, String firstName, String lastName, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void withdraw(BigDecimal amount) throws Exception{
        if (balance.compareTo(amount) < 0) {
            throw new Exception("Account " + id + " has no sufficient funds");
        } else {
            balance = balance.subtract(amount);
        }
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
