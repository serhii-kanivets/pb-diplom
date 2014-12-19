package com.pb.kanivets.bki.spring.business;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.exceptions.BusinessException;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.spring.dao.ClientDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringClientService implements IClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Client> listClients() {
        return clientDao.listClients();
    }

    @Override
    public Client getClient(int id) {
        return clientDao.getClient(id);
    }

    @Override
    public List<Client> searchByInn(String inn) {
        return clientDao.searchByInn(inn);
    }

    @Override
    public List<Client> searchByPassp(String passp) {
        return clientDao.searchByPassp(passp);
    }

    @Override
    public List<Client> searchByFIO(String sName, String fName, String mName) {
        return clientDao.searchByFIO(sName, fName, mName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Client addClient(Client client) {
        checkInn(client.getInn());
        checkPassp(client.getPassp());
        int newId = clientDao.newId();
        client.setClientId(newId);
        System.out.println("new Client " + client);
        clientDao.addClient(client);
        return client;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClient(Client newInfo) {
        Client oldClient = clientDao.getClient(newInfo.getClientId());
        if (oldClient == null) {
            throw new BusinessException("Невозможно обновить информацию: клиента не существует");
        }
        if (!oldClient.getInn().equalsIgnoreCase(newInfo.getInn())) {
            checkInn(newInfo.getInn());
        }
        if (!oldClient.getPassp().equalsIgnoreCase(newInfo.getPassp())) {
            checkPassp(newInfo.getPassp());
        }
        clientDao.updateClient(newInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteClient(int id) {
        clientDao.deleteClient(id);
    }

    public void checkInn(String inn) {
        if (clientDao.getByInn(inn) != null) {
            throw new BusinessException("Пользователь с ИНН " + inn + " уже существует");
        }
    }

    public void checkPassp(String passp) {
        if (clientDao.getByPassp(passp) != null) {
            throw new BusinessException("Пользователь с паспортом " + passp + " уже существует");
        }
    }

}
