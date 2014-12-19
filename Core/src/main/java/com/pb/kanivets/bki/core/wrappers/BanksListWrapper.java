package com.pb.kanivets.bki.core.wrappers;

import com.pb.kanivets.bki.core.entities.Bank;
import java.util.List;

public class BanksListWrapper extends ListWrapper<Bank>{

    public BanksListWrapper() {
    }

    public BanksListWrapper(List<Bank> list) {
        super(list);
    }
        
}
