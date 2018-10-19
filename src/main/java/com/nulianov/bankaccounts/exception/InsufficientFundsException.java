package com.nulianov.bankaccounts.exception;

import java.util.UUID;

/**
 * Shows that account has no sufficient funds to transfer
 */
public class InsufficientFundsException extends IllegalArgumentException {
    private final String message;

    public InsufficientFundsException(UUID accountId) {
        this.message = "Account " + accountId + " has no sufficient funds to make the transfer";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
