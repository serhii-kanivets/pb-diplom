package com.pb.kanivets.bki.core.services;

import com.pb.kanivets.bki.core.entities.Credit;
import java.util.List;

public interface ICreditService {
    List<Credit> listCredits();
    List<Credit> listCredits(int clientId);
    Credit getCredit(int creditId);
    Credit addCredit(Credit credit);
    //void deleteCredit(int id);
    void updateCredit(Credit newInfo);
    
}
