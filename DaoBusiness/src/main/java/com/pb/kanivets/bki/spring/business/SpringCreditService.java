package com.pb.kanivets.bki.spring.business;

import com.pb.kanivets.bki.core.entities.Credit;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.spring.dao.CreditDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringCreditService implements ICreditService {

    @Autowired
    private CreditDao creditDao;

    @Override
    public List<Credit> listCredits() {
        return creditDao.listCredits();
    }

    @Override
    public List<Credit> listCredits(int clientId) {
        return creditDao.listCredits(clientId);
    }

    @Override
    public Credit getCredit(int creditId) {
        return creditDao.getCredit(creditId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Credit addCredit(Credit credit) {
        System.out.println("addCredit");
        credit.setId(creditDao.newId());
        creditDao.addCredit(credit);
        return credit;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCredit(Credit newInfo) {
        System.out.println("updateCredit");
        creditDao.updateCredit(newInfo);
    }
    
}
