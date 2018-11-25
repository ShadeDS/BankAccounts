package com.nulianov.bankaccounts.domain;

import com.nulianov.bankaccounts.exception.IllegalAmountOfMoneyForTransactionException;
import com.nulianov.bankaccounts.exception.InsufficientFundsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountEntityTest {
    private static final BigDecimal startBalance = new BigDecimal(100);
    private static final BigDecimal amount = new BigDecimal(1000);
    private static final BigDecimal correctAmountToWithdraw = new BigDecimal(10);
    private static final BigDecimal illegalAmount = new BigDecimal(-1000);
    private Account account;

    @Before
    public void setUp() {
        account = new Account("john", "doe", startBalance);
    }

    @Test(expected = IllegalAmountOfMoneyForTransactionException.class)
    public void depositIllegalAmountOfMoney() {
        account.deposit(illegalAmount);
    }

    @Test(expected = IllegalAmountOfMoneyForTransactionException.class)
    public void withdrawIllegalAmountOfMoney() {
        account.withdraw(illegalAmount);
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawMoreThanBalance() {
        account.withdraw(amount);
    }

    @Test
    public void depositCorrectAmount() {
        account.deposit(amount);

        Assert.assertEquals(startBalance.add(amount), account.getBalance());
    }

    @Test
    public void withdrawCorrectAmount() {
        account.withdraw(correctAmountToWithdraw);

        Assert.assertEquals(startBalance.subtract(correctAmountToWithdraw), account.getBalance());
    }
}
