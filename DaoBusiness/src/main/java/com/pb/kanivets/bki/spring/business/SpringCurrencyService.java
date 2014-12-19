package com.pb.kanivets.bki.spring.business;

import com.pb.kanivets.bki.core.entities.Currency;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import com.pb.kanivets.bki.spring.dao.CurrencyDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringCurrencyService implements ICurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public List<Currency> listCurrencies() {
        return currencyDao.list();
    }

    @Override
    public Currency getCurrency(String currId) {
        Currency curr = currencyDao.get(currId);
        if (curr != null) {
            return curr;
        } else {
            throw new BusinessException("Валюты с кодом " + currId + " не существует");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCurrency(Currency curr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCurrency(Currency newInfo) {
        Currency curr = currencyDao.get(newInfo.getCurrId());
        if (curr != null) {
            currencyDao.update(newInfo);
        } else {
            currencyDao.add(newInfo);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCurrency(String currId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
