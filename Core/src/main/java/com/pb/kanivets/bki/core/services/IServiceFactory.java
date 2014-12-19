package com.pb.kanivets.bki.core.services;

public interface IServiceFactory {

    IBankService getBankService();

    IClientService getClientService();

    ICreditService getCreditService();

    ICurrencyService getCurrencyService();
    
}
