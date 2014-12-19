package com.pb.kanivets.bki.core.wrappers;

import com.pb.kanivets.bki.core.entities.Client;
import java.util.List;

public class ClientsListWrapper extends ListWrapper<Client> {

    public ClientsListWrapper() {
    }

    public ClientsListWrapper(List<Client> list) {
        super(list);
    }

}
