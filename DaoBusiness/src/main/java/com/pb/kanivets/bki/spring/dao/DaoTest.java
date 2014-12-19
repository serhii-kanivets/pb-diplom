package com.pb.kanivets.bki.spring.dao;

import java.math.BigDecimal;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DaoTest {
    public static void main(String[] args) {
        DataSource ds = new DriverManagerDataSource("jdbc:sybase:Tds:krammba-PC:5000/bki", "sa", "");
        JdbcTemplate template = new JdbcTemplate(ds);        
        int n = template.update("UPDATE credits SET delay = ? WHERE id = ?", "y   ",1);
        System.out.println("n = " + n);
    }
    
}
