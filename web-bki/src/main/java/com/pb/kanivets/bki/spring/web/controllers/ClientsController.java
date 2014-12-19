package com.pb.kanivets.bki.spring.web.controllers;

import com.pb.kanivets.bki.core.entities.Client;
import com.pb.kanivets.bki.core.services.IClientService;
import com.pb.kanivets.bki.core.wrappers.ClientsListWrapper;
import com.pb.kanivets.bki.core.wrappers.ListWrapper;
import com.pb.kanivets.bki.core.wrappers.StringArrayWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private IClientService clientService;

    @RequestMapping(method = RequestMethod.GET)
    public ListWrapper list() {
        return new ClientsListWrapper(clientService.listClients());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Client add(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Client update(@RequestBody Client client) {
        clientService.updateClient(client);
        return client;
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        clientService.deleteClient(id);
    }

    @RequestMapping("/{id}")
    public Client getClient(@PathVariable int id) {
        return clientService.getClient(id);
    }

    @RequestMapping("/search/byinn")
    public ClientsListWrapper searchByInn(@RequestParam String inn) {
        return new ClientsListWrapper(clientService.searchByInn(inn));
    }

    @RequestMapping("/search/bypassp")
    public ClientsListWrapper searchByPassp(@RequestParam String passp) {
        return new ClientsListWrapper(clientService.searchByPassp(passp));
    }

    @RequestMapping("/search/byfio")
    public ListWrapper searchByPassp(@RequestParam String sName, @RequestParam String fName, @RequestParam String mName) {
        return new ListWrapper(clientService.searchByFIO(sName, fName, mName));
    }

}
