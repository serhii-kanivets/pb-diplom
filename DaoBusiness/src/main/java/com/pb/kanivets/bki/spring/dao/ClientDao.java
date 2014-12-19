package com.pb.kanivets.bki.spring.dao;

import com.pb.kanivets.bki.core.entities.Client;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String LIST_QUERY = "SELECT clientID, inn, sName, fName, mName, birthDate, passp FROM clients WHERE deleted IS NULL";
    private final String CLIENT_QUERY = "SELECT clientID, inn, sName, fName, mName, birthDate, passp FROM clients WHERE clientID=? AND deleted IS NULL";
    private final String ADD_QUERY = "INSERT INTO clients (clientID, inn, sName, fName, mName, birthDate, passp) values (?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_QUERY = "UPDATE clients SET deleted = ? WHERE clientID = ?";
    private final String UPDATE_QUERY = "UPDATE clients SET inn=?, sName=?, fName=?, mName=?, birthDate=?, passp=? WHERE clientID=?";
    private final String NEW_ID_QUERY = "update ids set maxId=maxId+1 where entity='clients' "
            + "select maxId from ids where entity='clients' ";//"exec nextID clients";

    private final RowMapper<Client> clientMapper = new RowMapper<Client>() {

        @Override
        public Client mapRow(ResultSet rs, int i) throws SQLException {
            Client client = new Client();
            client.setClientId(rs.getInt("clientId"));
            client.setInn(rs.getNString("inn"));
            client.setfName(rs.getNString("fName"));
            client.setsName(rs.getNString("sName"));
            client.setmName(rs.getNString("mName"));
            client.setBirthDate(rs.getDate("birthDate"));
            client.setPassp(rs.getNString("passp"));
            return client;
        }
    };

    public List<Client> listClients() {
        return jdbcTemplate.query(LIST_QUERY, clientMapper);
    }

    public Client getClient(int id) {
        Client c = null;
        try {
            c = jdbcTemplate.queryForObject(LIST_QUERY + " AND clientId=?", clientMapper, id);
        } catch (EmptyResultDataAccessException e) {
            //NOP
        }
        return c;
    }

    public Client getByInn(String inn) {
        Client c = null;
        try {
            c = jdbcTemplate.queryForObject(LIST_QUERY + " AND inn=?", clientMapper, inn);
        } catch (EmptyResultDataAccessException e) {
            //NOP
        }
        return c;
    }

    public Client getByPassp(String passp) {
        Client c = null;
        try {
            c = jdbcTemplate.queryForObject(LIST_QUERY + " AND passp=?", clientMapper, passp);
        } catch (EmptyResultDataAccessException e) {
            //NOP
        }
        return c;
    }

    public List<Client> searchByFIO(String sName, String fName, String mName) {
        String query = LIST_QUERY;
        List<String> queryparams = new ArrayList<>();
        if (sName.length() > 0) {
            query = query + " AND sName LIKE ?";
            queryparams.add(sName + "%");
        }
        if (fName.length() > 0) {
            query = query + " AND fName LIKE ?";
            queryparams.add(fName + "%");
        }
        if (mName.length() > 0) {
            query = query + " AND mName LIKE ?";
            queryparams.add(mName + "%");
        }
        return jdbcTemplate.query(query, clientMapper, queryparams.toArray());
    }

    public void addClient(Client client) {
        jdbcTemplate.update(ADD_QUERY, client.getClientId(), client.getInn(),
                client.getsName(), client.getfName(), client.getmName(), client.getBirthDate(), client.getPassp());
    }

    public int newId() {
        return jdbcTemplate.queryForObject(NEW_ID_QUERY, Integer.class);
    }

    public void deleteClient(int id) {
        jdbcTemplate.update(DELETE_QUERY, new Date(System.currentTimeMillis()), id);
    }

    public void updateClient(Client client) {
        jdbcTemplate.update(UPDATE_QUERY, client.getInn(),
                client.getsName(), client.getfName(), client.getmName(), client.getBirthDate(), client.getPassp(), client.getClientId());
    }

    public List<Client> searchByInn(String inn) {
        return jdbcTemplate.query(LIST_QUERY + " AND inn LIKE ?", clientMapper, inn + "%");
    }

    public List<Client> searchByPassp(String passp) {
        return jdbcTemplate.query(LIST_QUERY + " AND passp LIKE ?", clientMapper, passp + "%");
    }
}
