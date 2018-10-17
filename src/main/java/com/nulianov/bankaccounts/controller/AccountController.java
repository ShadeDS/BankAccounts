package com.nulianov.bankaccounts.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.service.AccountService;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

public class AccountController {
    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Inject
    private AccountService accountService;

    @Inject
    Gson gson;

    public Route getAccount() {
        return (request, response) -> {
            String id = request.params(":id");
            log.info("Get account with id {}", id);
            Account account = accountService.getAccount(id);

            if (account != null) {
                response.status(HttpStatus.OK_200);
                return gson.toJson(account);
            } else {
                response.status(HttpStatus.NOT_FOUND_404);
                response.body("Account " + id + " was not found in database");
                return response.body();
            }
        };
    }

    public Route createAccount(){
        return (request, response) -> {
            log.info("Create account");
            Account user = gson.fromJson(request.body(), Account.class);

            try {
                accountService.addAccount(user);
            } catch (Exception e){
                response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                return e.getMessage();
            }

            response.status(HttpStatus.CREATED_201);
            return gson.toJson(user);
        };
    }

    public Route deleteAccount() {
        return (request, response) -> {
            String id = request.params(":id");
            log.info("Delete account with id {}", id);
            try {
                accountService.deleteAccount(id);
            } catch (Exception e) {
                response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                response.body(e.getMessage());
                return response.body();
            }
            response.status(HttpStatus.OK_200);
            response.body("Account " + id + " was deleted successfully");
            return response.body();
        };
    }
}
