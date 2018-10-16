package com.nulianov.bankaccounts.domain.serializer;

import com.nulianov.bankaccounts.domain.Account;
import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

public class CustomAccountSerializer implements Serializer<Account>, Serializable {
    @Override
    public void serialize(@NotNull DataOutput2 out, @NotNull Account value) throws IOException {
        out.writeUTF(value.getId());
        out.writeUTF(value.getFirstName());
        out.writeUTF(value.getLastName());
        out.writeUTF(value.getBalance().toString());
    }

    @Override
    public Account deserialize(@NotNull DataInput2 input, int available) throws IOException {
        return new Account(input.readUTF(), input.readUTF(), input.readUTF(), new BigDecimal(input.readUTF()));
    }
}
