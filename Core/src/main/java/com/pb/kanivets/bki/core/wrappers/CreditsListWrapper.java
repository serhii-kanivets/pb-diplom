package com.pb.kanivets.bki.core.wrappers;

import com.pb.kanivets.bki.core.entities.Credit;
import java.util.List;

public class CreditsListWrapper  extends ListWrapper<Credit>{

    public CreditsListWrapper() {
    }

    public CreditsListWrapper(List<Credit> list) {
        super(list);
    }
    
}
