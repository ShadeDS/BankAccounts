package com.nulianov.bankaccounts.service;

import com.nulianov.bankaccounts.domain.Account;

public interface AccountService {
    void addAccount(Account account) throws Exception;

    Account getAccount(String id);

    void deleteAccount(String id);

    void updateAccount(Account account) throws Exception;
}
