package com.pb.kanivets.bki.core.wrappers;

import com.pb.kanivets.bki.core.entities.Currency;
import java.util.List;

public class CurrenciesListWrapper extends ListWrapper<Currency> {

    public CurrenciesListWrapper() {
    }

    public CurrenciesListWrapper(List<Currency> list) {
        super(list);
    }
    
}
