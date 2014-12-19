package com.pb.kanivets.bki.spring.web.controllers;

import com.pb.kanivets.bki.core.entities.Currency;
import com.pb.kanivets.bki.core.services.ICurrencyService;
import com.pb.kanivets.bki.core.wrappers.CurrenciesListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    @Autowired
    private ICurrencyService currencyService;
   
    @RequestMapping
    public CurrenciesListWrapper list(){
        return new CurrenciesListWrapper(currencyService.listCurrencies());
    }
    
    @RequestMapping(value = "/{currId}" , method = RequestMethod.GET)
    public Currency get(@PathVariable String currId){
        return currencyService.getCurrency(currId);
    }
    
    @RequestMapping(value = "/{currId}", method = RequestMethod.POST)
    public Currency update(@RequestBody Currency curr){
        currencyService.updateCurrency(curr);
        return curr;
    }
}
