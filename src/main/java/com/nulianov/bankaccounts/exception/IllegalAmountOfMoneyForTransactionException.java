package com.nulianov.bankaccounts.exception;

import java.math.BigDecimal;

/**
 * Shows that received amount for transfer is invalid
 */
public class IllegalAmountOfMoneyForTransactionException extends IllegalArgumentException {
    private final String message;

    public IllegalAmountOfMoneyForTransactionException(BigDecimal amount) {
        this.message = "Illegal amount money to process: " + amount;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
