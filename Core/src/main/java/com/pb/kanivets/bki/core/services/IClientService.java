package com.pb.kanivets.bki.core.services;

import com.pb.kanivets.bki.core.entities.Client;
import java.util.List;

public interface IClientService {
    List<Client> listClients();
    Client getClient(int id);
    Client addClient(Client client);
    void updateClient(Client newInfo);
    void deleteClient(int id);    
    List<Client> searchByInn(String inn);
    List<Client> searchByPassp(String passp);
    List<Client> searchByFIO(String sName, String fName, String mName);
}
