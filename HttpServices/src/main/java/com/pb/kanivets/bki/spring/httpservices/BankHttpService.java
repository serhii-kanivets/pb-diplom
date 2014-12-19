package com.pb.kanivets.bki.spring.httpservices;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.core.wrappers.BanksListWrapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BankHttpService implements IBankService {
    
    RestTemplateRequestor requestor = new RestTemplateRequestor();    

    @Override
    public List<Bank> listBanks() {
        return requestor.doRequest("banks/", BanksListWrapper.class).getList();
    }

    @Override
    public Bank getBank(int mfo) {
        return requestor.doRequest("banks/" + mfo, Bank.class);
    }

    @Override
    public void addBank(Bank bank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBank(Bank newInfo) {
        System.out.println("" + newInfo);
        requestor.doPostRequest("banks/" + newInfo.getMfo(), newInfo);
    }

    @Override
    public void deleteBank(int mfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
