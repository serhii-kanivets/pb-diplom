package com.pb.kanivets.bki.spring.business;

import com.pb.kanivets.bki.core.entities.Bank;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.services.IBankService;
import com.pb.kanivets.bki.spring.dao.BankDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringBankService implements IBankService {

    @Autowired
    private BankDao bankDao;

    @Override
    public List<Bank> listBanks() {
        return bankDao.list();
    }

    @Override
    public Bank getBank(int mfo) {
        Bank bank = bankDao.get(mfo);
        if (bank != null) {
            return bank;
        } else {
            throw new BusinessException("Банка с МФО " + mfo + " нет в справочнике");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addBank(Bank bank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBank(Bank newInfo) {
        if (bankDao.get(newInfo.getMfo()) != null) {
            bankDao.update(newInfo);
        } else {
            bankDao.add(newInfo);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBank(int mfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
