package com.pb.kanivets.bki.spring.web.controllers;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.core.wrappers.BanksListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private IBankService bankService;

    @RequestMapping
    public BanksListWrapper list() {
        return new BanksListWrapper(bankService.listBanks());
    }

    @RequestMapping(value = "/{mfo}", method = RequestMethod.GET)
    public Bank get(@PathVariable int mfo) {
        return bankService.getBank(mfo);
    }

    @RequestMapping(value = "/{mfo}", method = RequestMethod.POST)
    public Bank update(@RequestBody Bank bank) {
        bankService.updateBank(bank);
        return bank;
    }
}
