package com.pb.kanivets.bki.spring.dao;

import com.pb.kanivets.bki.core.entities.Bank;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BankDao {

    @Autowired
    private JdbcTemplate template;

    private static final String LIST_QUERY = "SELECT mfo, name FROM bank";
    private static final String UPDATE_QUERY = "UPDATE bank SET name = ? WHERE mfo = ?";
    private static final String ADD_QUERY = "INSERT INTO bank (mfo, name) VALUES (?, ?)";

    private RowMapper<Bank> bankRowMapper = new RowMapper<Bank>() {

        @Override
        public Bank mapRow(ResultSet rs, int i) throws SQLException {
            Bank bank = new Bank();
            bank.setMfo(rs.getInt("mfo"));
            bank.setName(rs.getNString("name"));
            return bank;
        }
    };

    public List<Bank> list() {
        return template.query(LIST_QUERY, bankRowMapper);
    }

    public Bank get(int mfo) {
        Bank bank = null;
        try {
            bank = template.queryForObject(LIST_QUERY + " WHERE mfo = " + mfo, bankRowMapper);
        } catch (EmptyResultDataAccessException e) {
            //NOP;
        }
        return bank;
    }

    public int add(Bank bank) {
        return template.update(ADD_QUERY, bank.getMfo(), bank.getName());
    }

    public int update(Bank bank) {
        return template.update(UPDATE_QUERY, bank.getName(), bank.getMfo());
    }

}
