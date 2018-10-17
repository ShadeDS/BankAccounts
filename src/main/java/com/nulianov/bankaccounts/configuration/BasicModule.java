package com.nulianov.bankaccounts.configuration;

import com.google.inject.AbstractModule;
import com.nulianov.bankaccounts.service.AccountService;
import com.nulianov.bankaccounts.service.impl.AccountServiceImpl;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
