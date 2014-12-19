package com.pb.kanivets.bki.core.services;

import com.pb.kanivets.bki.core.entities.Bank;
import java.util.List;

public interface IBankService {
    List<Bank> listBanks();
    Bank getBank(int mfo);
    void addBank(Bank bank);
    void updateBank(Bank newInfo);
    void deleteBank(int mfo);
}
