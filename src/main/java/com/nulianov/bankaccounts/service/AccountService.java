package com.nulianov.bankaccounts.service;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;

public interface AccountService {
    void addAccount(Account account) throws Exception;

    Account getAccount(String id);

    void deleteAccount(String id);

    void transfer(Transfer transfer) throws Exception;
}
