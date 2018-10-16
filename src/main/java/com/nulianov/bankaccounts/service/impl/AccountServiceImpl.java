package com.nulianov.bankaccounts.service.impl;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.domain.serializer.CustomAccountSerializer;
import com.nulianov.bankaccounts.service.AccountService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.Map;

public class AccountServiceImpl implements AccountService {
    public static volatile DB db = DBMaker.memoryDB().transactionEnable().closeOnJvmShutdown().make();
    @Override
    public void addAccount(Account account) throws Exception{
        Map<String, Account> storage = getStorage(db);
        if (storage.get(account.getId()) != null) {
            throw new Exception("Account with id " + account.getId() + " already exists");
        }
        storage.put(account.getId(), account);
        db.commit();
    }

    @Override
    public Account getAccount(String id) {
        Map<String, Account> storage = getStorage(db);
        return storage.get(id);
    }

    @Override
    public void deleteAccount(String id) {
        Map<String, Account> storage = getStorage(db);
        storage.remove(id);
        db.commit();
    }

    @Override
    public void transfer(Transfer transfer) throws Exception {
        Map<String, Account> storage = getStorage(db);
        Account from = getAccount(transfer.getFrom());
        Account to = getAccount(transfer.getTo());

        from.withdraw(transfer.getAmount());
        to.deposit(transfer.getAmount());

        storage.put(from.getId(), from);
        storage.put(to.getId(), to);
        db.commit();
    }

    private Map<String, Account> getStorage(DB db) {
        return db.hashMap("Accounts").keySerializer(Serializer.STRING).valueSerializer(new CustomAccountSerializer()).createOrOpen();
    }
}
