package com.pb.kanivets.bki.spring.httpservices;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.core.wrappers.ClientsListWrapper;
import com.pb.kanivets.bki.core.wrappers.StringArrayWrapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClientHttpService implements IClientService {

    private final RestTemplateRequestor requestor;

    public ClientHttpService() {
        this.requestor = new RestTemplateRequestor();
    }

    @Override
    public List<Client> listClients() {
        return requestor.doRequest("clients", ClientsListWrapper.class).getList();
    }

    @Override
    public Client getClient(int id) {
        return requestor.doRequest("clients/" + id, Client.class);
    }

    @Override
    public Client addClient(Client client) {
        return requestor.doPostRequest("clients/add", client);
    }

    @Override
    public void updateClient(Client newInfo) {
        requestor.doPostRequest("clients", newInfo);
    }

    @Override
    public void deleteClient(int id) {
        requestor.doRequest("clients/delete/" + id, Object.class);
    }

    @Override
    public List<Client> searchByInn(String inn) {
        return requestor.doRequest("clients/search/byinn?inn=" + inn, ClientsListWrapper.class).getList();
    }

    @Override
    public List<Client> searchByPassp(String passp) {
        return requestor.doRequest("clients/search/bypassp?passp=" + passp, ClientsListWrapper.class).getList();
    }

    @Override
    public List<Client> searchByFIO(String sName, String fName, String mName) {        
        return requestor.doRequest("clients/search/byfio?sName=" + sName + "&fName=" + fName + "&mName=" + mName, ClientsListWrapper.class).getList();
    }

}
