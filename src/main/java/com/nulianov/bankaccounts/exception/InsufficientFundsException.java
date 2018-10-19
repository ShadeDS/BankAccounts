package com.nulianov.bankaccounts.exception;

import java.util.UUID;

/**
 * Shows that account has no sufficient funds to transfer
 */
public class InsufficientFundsException extends IllegalArgumentException {
    private final UUID id;

    public InsufficientFundsException(UUID id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Account " + id + " has no sufficient funds to make the transfer";
    }
}
