package com.nulianov.bankaccounts.service.impl;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.domain.serializer.CustomAccountSerializer;
import com.nulianov.bankaccounts.exception.AccountDuplicationException;
import com.nulianov.bankaccounts.exception.InsufficientFundsException;
import com.nulianov.bankaccounts.service.AccountService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountServiceImpl implements AccountService {
    private static final Map<UUID, Boolean> lockedAccounts = new ConcurrentHashMap<>();
    private static final DB db = DBMaker.memoryDB().closeOnJvmShutdown().make();

    @Override
    public void addAccount(Account account) throws AccountDuplicationException{
        Map<UUID, Account> storage = getStorage();
        if (storage.get(account.getId()) != null) {
            throw new AccountDuplicationException(account.getId());
        }
        storage.put(account.getId(), account);
        db.commit();
    }

    @Override
    public Account getAccount(UUID id) {
        return getStorage().get(id);
    }

    @Override
    public void deleteAccount(UUID id) throws Exception {
        while (lockedAccounts.putIfAbsent(id, true) != null) {
            Thread.sleep(1);
        }
        try {
            getStorage().remove(id);
            db.commit();
        } finally {
            lockedAccounts.remove(id);
        }
    }

    @Override
    public void transfer(Transfer transfer) throws InterruptedException, InsufficientFundsException {
        Map<UUID, Account> storage = getStorage();

        UUID lockFirst, lockSecond;
        if (transfer.getFrom().compareTo(transfer.getTo()) > 0) {
            lockFirst = transfer.getTo();
            lockSecond = transfer.getFrom();
        } else {
            lockFirst = transfer.getFrom();
            lockSecond = transfer.getTo();
        }

        while (lockedAccounts.putIfAbsent(lockFirst, true) != null) {
            Thread.sleep(1);
        }

        while (lockedAccounts.putIfAbsent(lockSecond, true) != null) {
            Thread.sleep(1);
        }
        try {
            Account from = getAccount(transfer.getFrom());
            Account to = getAccount(transfer.getTo());

            from.withdraw(transfer.getAmount());
            to.deposit(transfer.getAmount());

            storage.put(from.getId(), from);
            storage.put(to.getId(), to);
            db.commit();
        } finally {
            lockedAccounts.remove(lockSecond);
            lockedAccounts.remove(lockFirst);
        }
    }

    private Map<UUID, Account> getStorage() {
        return db.hashMap("Accounts").keySerializer(Serializer.UUID).valueSerializer(new CustomAccountSerializer()).createOrOpen();
    }
}
