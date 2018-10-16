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
            Transfer transferInfo = new Gson().fromJson(request.body(), Transfer.class);
            log.info("Transfer from {} to {}", transferInfo.getFrom(), transferInfo.getTo());
            accountService.transfer(transferInfo);
       } catch (Exception e){
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return e.getMessage();
        }

        response.status(HttpStatus.OK_200);
        response.body("Transfer processed successfully");
        return response.body();
    };
}
