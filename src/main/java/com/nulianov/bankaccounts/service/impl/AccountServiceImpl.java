package com.nulianov.bankaccounts.service.impl;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.domain.serializer.CustomAccountSerializer;
import com.nulianov.bankaccounts.service.AccountService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountServiceImpl implements AccountService {
    private static final Map<String, Boolean> lockedAccounts = new ConcurrentHashMap<>();
    private static final DB db = DBMaker.memoryDB().closeOnJvmShutdown().make();

    @Override
    public void addAccount(Account account) throws Exception{
        Map<String, Account> storage = getStorage();
        if (storage.get(account.getId()) != null) {
            throw new Exception("Account with id " + account.getId() + " already exists");
        }
        storage.put(account.getId(), account);
        db.commit();
    }

    @Override
    public Account getAccount(String id) {
        return getStorage().get(id);
    }

    @Override
    public void deleteAccount(String id) throws Exception {
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
    public void transfer(Transfer transfer) throws Exception {
        Map<String, Account> storage = getStorage();

        String lockFirst, lockSecond;
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

    private Map<String, Account> getStorage() {
        return db.hashMap("Accounts").keySerializer(Serializer.STRING).valueSerializer(new CustomAccountSerializer()).createOrOpen();
    }
}
