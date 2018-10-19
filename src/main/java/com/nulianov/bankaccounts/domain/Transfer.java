package com.nulianov.bankaccounts.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Transfer {
    private UUID from;
    private UUID to;
    private BigDecimal amount;

    public Transfer(UUID from, UUID to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
