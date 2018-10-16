package com.nulianov.bankaccounts.controller;

import com.google.gson.Gson;
import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.service.AccountService;
import com.nulianov.bankaccounts.service.impl.AccountServiceImpl;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class AccountController {
    private static Logger log = LoggerFactory.getLogger(AccountController.class);
    private static AccountService accountService = new AccountServiceImpl();

    public static Route getAccount = (Request request, Response response) -> {
        String id = request.params(":id");
        log.info("Get account with id {}", id);
        Account account = accountService.getAccount(id);

        if (account != null) {
            response.status(HttpStatus.OK_200);
            return new Gson().toJson(account);
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
            response.body("Account " + id + " was not found in database");
            return response.body();
        }
    };

    public static Route createAccount = (Request request, Response response) -> {
        log.info("Create account");
        Account user = new Gson().fromJson(request.body(), Account.class);

        try {
            accountService.addAccount(user);
        } catch (Exception e){
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return e.getMessage();
        }

        response.status(HttpStatus.CREATED_201);
        return new Gson().toJson(user);
    };

    // TODO: lock
    public static Route deleteAccount = (Request request, Response response) -> {
        String id = request.params(":id");
        log.info("Delete account with id {}", id);
        accountService.deleteAccount(id);
        response.status(HttpStatus.OK_200);
        response.body("Account " + id + " was deleted successfully");
        return response.body();
    };
}
