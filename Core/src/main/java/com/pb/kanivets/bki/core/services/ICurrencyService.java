package com.pb.kanivets.bki.core.services;

import com.pb.kanivets.bki.core.entities.Currency;
import java.util.List;

public interface ICurrencyService {
    List<Currency> listCurrencies();
    Currency getCurrency(String currId);
    void addCurrency(Currency curr);
    void updateCurrency(Currency newInfo);
    void deleteCurrency(String currId);
}
