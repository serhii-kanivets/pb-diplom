package com.pb.kanivets.bki.spring.httpservices;

import com.pb.kanivets.bki.core.entities.Currency;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import com.pb.kanivets.bki.core.wrappers.CurrenciesListWrapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CurrencyHttpService implements ICurrencyService {
    
    RestTemplateRequestor requestor = new RestTemplateRequestor();
    
    @Override
    public List<Currency> listCurrencies() {
        return requestor.doRequest("currencies", CurrenciesListWrapper.class).getList();
    }

    @Override
    public Currency getCurrency(String currId) {
        return requestor.doRequest("currencies/" + currId, Currency.class);
    }

    @Override
    public void addCurrency(Currency curr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCurrency(Currency newInfo) {
        requestor.doPostRequest("currencies/" + newInfo.getCurrId(), newInfo);
    }

    @Override
    public void deleteCurrency(String currId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
