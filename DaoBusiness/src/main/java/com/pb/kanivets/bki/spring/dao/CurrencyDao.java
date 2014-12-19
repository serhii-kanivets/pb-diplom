package com.pb.kanivets.bki.spring.dao;

import com.pb.kanivets.bki.core.entities.Currency;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDao {

    @Autowired
    private JdbcTemplate template;

    private static final String LIST_QUERY = "SELECT currID, currName FROM currency";
    private static final String UPDATE_QUERY = "UPDATE currency SET currName = ? WHERE currID = ?";
    private static final String ADD_QUERY = "INSERT INTO currency (currID, currName) VALUES (?, ?)";
    private final RowMapper<Currency> currRowMapepr = new RowMapper<Currency>() {

        @Override
        public Currency mapRow(ResultSet rs, int i) throws SQLException {
            Currency curr = new Currency();
            curr.setCurrId(rs.getNString("currId"));
            curr.setName(rs.getNString("currName"));
            return curr;
        }

    };

    public List<Currency> list() {
        return template.query(LIST_QUERY, currRowMapepr);
    }

    public Currency get(String currId) {
        Currency curr = null;
        try {
            curr = template.queryForObject(LIST_QUERY + " WHERE currID = ? ", currRowMapepr, currId);
        } catch (EmptyResultDataAccessException e) {
            //NOP;
        }
        return curr;
    }

    public int update(Currency curr) {
        return template.update(UPDATE_QUERY, curr.getName(), curr.getCurrId());
    }

    public int add(Currency curr) {
        return template.update(ADD_QUERY, curr.getCurrId(), curr.getName());
    }

}
