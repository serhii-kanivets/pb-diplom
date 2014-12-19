package com.pb.kanivets.bki.spring.web.controllers;

import com.pb.kanivets.bki.core.entities.Credit;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.core.wrappers.CreditsListWrapper;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "credits")
public class CreditController {

    @Autowired
    private ICreditService creditService;

    @RequestMapping(method = RequestMethod.GET)
    public CreditsListWrapper listCredits() {
        return new CreditsListWrapper(creditService.listCredits());
    }

    @RequestMapping("/ofclient/{clientId}")
    public CreditsListWrapper listCredits(@PathVariable int clientId) {
        return new CreditsListWrapper(creditService.listCredits(clientId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Credit getCredit(@PathVariable int id) {
        return creditService.getCredit(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Credit addCredit(@RequestBody Credit credit) {
        System.out.println("Controller add Credit");
        return creditService.addCredit(credit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Credit updateCredit(@RequestBody Credit newInfo) {
        creditService.updateCredit(newInfo);
        return newInfo;
    }

}
