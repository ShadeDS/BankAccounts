package com.nulianov.bankaccounts.service.impl;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.service.AccountService;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {

    private static Map<String, Account> storage = new HashMap<>();
    @Override
    public void addAccount(Account account) throws Exception{
        if (storage.get(account.getId()) != null) {
            throw new Exception("Account with id " + account.getId() + " already exists");
        }
        storage.put(account.getId(), account);
    }

    @Override
    public Account getAccount(String id) {
        return storage.get(id);
    }

    @Override
    public void deleteAccount(String id) {
        storage.remove(id);
    }

    @Override
    public void updateAccount(Account account) throws Exception {
        if (storage.get(account.getId()) == null) {
            throw new Exception("Account with id " + account.getId() + " is not present");
        }
        storage.put(account.getId(), account);
    }
}
