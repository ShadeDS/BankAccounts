package com.nulianov.bankaccounts;

import com.nulianov.bankaccounts.controller.AccountController;
import com.nulianov.bankaccounts.controller.TransferController;

import static spark.Spark.*;

public class Application {

    //TODO: in memory database
    //TODO: DI
    public static void main(String[] args) {
        path("/api", () -> {
            path("/account", () -> {
                post("", AccountController.createAccount);
                get("/:id", AccountController.getAccount);
                delete("/:id", AccountController.deleteAccount);
            });
            post("/transfer", TransferController.transfer);
        });
    }
}
