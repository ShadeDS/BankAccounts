package com.nulianov.bankaccounts.controller;

import com.google.gson.Gson;
import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.service.AccountService;
import com.nulianov.bankaccounts.service.impl.AccountServiceImpl;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class TransferController {
    private static Logger log = LoggerFactory.getLogger(TransferController.class);
    private static AccountService accountService = new AccountServiceImpl();

    public static Route transfer = (Request request, Response response) -> {
        try {
            Transfer body = new Gson().fromJson(request.body(), Transfer.class);

            Account from = accountService.getAccount(body.getFrom());
            Account to = accountService.getAccount(body.getTo());

            log.info("Transfer from {} to {}", from.getId(), to.getId());

            Object firstLock, secondLock;
            if (from.getId().compareTo(to.getId()) > 0) {
                firstLock = from;
                secondLock = to;
            } else {
                firstLock = to;
                secondLock = from;
            }

            synchronized (firstLock) {
                synchronized (secondLock) {
                    from.withdraw(body.getAmount());
                    to.deposit(body.getAmount());

                    accountService.updateAccount(from);
                    accountService.updateAccount(to);
                }
            }

       } catch (Exception e){
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return e.getMessage();
        }

        response.status(HttpStatus.OK_200);
        response.body("Transfer processed successfully");
        return response.body();
    };
}
