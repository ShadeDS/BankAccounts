package com.nulianov.bankaccounts.service;

import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.exception.InsufficientFundsException;

import java.util.UUID;

public interface AccountService {
    /**
     * If there is no account with the same id puts the account into database,
     * else throws an exception
     * @param account to put into database
     * @throws Exception there is another account with the same id in database
     */
    void addAccount(Account account) throws Exception;

    /**
     * If an account with specified id is present in database returns this account,
     * else returns {@code null}
     * @param id identifies account to get
     * @return the account to which the specified id belongs, or
     * {@code null} if database doesn't contain account with specified id
     */
    Account getAccount(UUID id);

    /**
     * Removes an account with specified id from database
     * @param id identifies the account to remove
     * @throws Exception couldn't remove the account correctly
     */
    void deleteAccount(UUID id) throws Exception;

    /**
     * If both transfer participants are present in database and sender has
     * a sufficient amount of money processes the transfer,
     * else returns an exception
     * @param transfer transfer specifies the sender, the recipient and the amount of money to transfer
     * @throws InterruptedException thread was interrupted while trying to lock account
     * @throws InsufficientFundsException account has no sufficient funds to transfer
     */
    void transfer(Transfer transfer) throws InterruptedException, InsufficientFundsException;
}
