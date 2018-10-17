package com.nulianov.bankaccounts;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nulianov.bankaccounts.configuration.BasicModule;
import com.nulianov.bankaccounts.controller.AccountController;
import com.nulianov.bankaccounts.controller.TransferController;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        AccountController accountController = injector.getInstance(AccountController.class);
        TransferController transferController = injector.getInstance(TransferController.class);

        path("/api", () -> {
            path("/account", () -> {
                post("", accountController.createAccount());
                get("/:id", accountController.getAccount());
                delete("/:id", accountController.deleteAccount());
            });
            post("/transfer", transferController.transfer());
        });
    }
}
