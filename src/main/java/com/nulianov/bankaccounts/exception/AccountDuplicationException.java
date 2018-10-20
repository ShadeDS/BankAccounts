package com.nulianov.bankaccounts.exception;

import java.util.UUID;

/**
 * Shows that account with the same id already exists in database
 */
public class AccountDuplicationException extends IllegalArgumentException {
    private final String message;

    public AccountDuplicationException(UUID accountId) {
        this.message = "Account with id " + accountId + " already exists";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
