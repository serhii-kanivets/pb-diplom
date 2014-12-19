package com.pb.kanivets.bki.spring.dao;

import com.pb.kanivets.bki.core.entities.Credit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CreditDao {

    @Autowired
    private JdbcTemplate template;

    RowMapper<Credit> creditRowMapper = new RowMapper<Credit>() {
        @Override
        public Credit mapRow(ResultSet rs, int i) throws SQLException {
            Credit c = new Credit();
            c.setId(rs.getInt("id"));
            c.setClientId(rs.getInt("clientId"));
            c.setMfo(rs.getInt("mfo"));
            c.setCurrId(rs.getNString("currId"));
            c.setInitSum(rs.getBigDecimal("initSum"));
            c.setIssueDate(rs.getDate("issueDate"));
            c.setBody(rs.getBigDecimal("body"));
            c.setCloseDate(rs.getDate("closeDate"));
            c.setDelay(rs.getNString("delay"));
            return c;
        }
    };

    private static final String LIST_QUERY = "SELECT id, clientId, mfo, currId, initSum, issueDate, body, closeDate, delay FROM credits";
    private static final String ADD_QUERY = "INSERT INTO credits (id, clientId, mfo, currId, initSum, issueDate, body, closeDate, delay) values (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE credits SET clientId = ?, mfo = ?, currId = ?, initSum =?, issueDate = ?, body = ?, closeDate = ?, delay = ? WHERE id = ?";
    private static final String NEW_ID_QUERY = "UPDATE ids SET maxId=maxId+1 WHERE entity='credits' SELECT maxId FROM ids WHERE entity='credits'";

    public List<Credit> listCredits() {
        return template.query(LIST_QUERY, creditRowMapper);
    }

    public List<Credit> listCredits(int clientId) {
        return template.query(LIST_QUERY + " WHERE clientId = " + clientId, creditRowMapper);
    }

    public Credit getCredit(int id) {
        Credit credit = null;
        try {
            credit = template.queryForObject(LIST_QUERY + " WHERE id = " + id, creditRowMapper);
        } catch (EmptyResultDataAccessException e) {
            //NOP;
        }
        return credit;
    }

    public void addCredit(Credit c) {
        template.update(ADD_QUERY, c.getId(), c.getClientId(), c.getMfo(), c.getCurrId(), c.getInitSum(), c.getIssueDate(), c.getBody(), c.getCloseDate(), c.getDelay());
    }

    public int newId() {
        return template.queryForObject(NEW_ID_QUERY, Integer.class);
    }

    public void updateCredit(Credit c) {
        template.update(UPDATE_QUERY, c.getClientId(), c.getMfo(), c.getCurrId(), c.getInitSum(), c.getIssueDate(), c.getBody(), c.getCloseDate(), c.getDelay(), c.getId());
    }
}
