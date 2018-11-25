package com.nulianov.bankaccounts.exception;

import java.util.UUID;

/**
 * Shows that account is absent in storage
 */
public class AccountNotFoundException extends IllegalArgumentException {
    private final String message;

    public AccountNotFoundException(UUID accountId) {
        this.message = "Account " + accountId + " was not found";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
