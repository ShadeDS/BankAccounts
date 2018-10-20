package com.nulianov.bankaccounts.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.nulianov.bankaccounts.domain.Transfer;
import com.nulianov.bankaccounts.exception.InsufficientFundsException;
import com.nulianov.bankaccounts.service.AccountService;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

public class TransferController {
    private static final Logger log = LoggerFactory.getLogger(TransferController.class);

    @Inject
    private AccountService accountService;

    @Inject
    private Gson gson;

    public Route transfer() {
        return (request, response) -> {
            try {
                Transfer transferInfo = gson.fromJson(request.body(), Transfer.class);
                log.info("Transfer from {} to {}", transferInfo.getFrom(), transferInfo.getTo());
                accountService.transfer(transferInfo);
            } catch (InsufficientFundsException e) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return e.getMessage();
            } catch (Exception e) {
                response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                return "Internal error occurred";
            }

            response.status(HttpStatus.OK_200);
            response.body("Transfer processed successfully");
            return response.body();
        };
    }
}
