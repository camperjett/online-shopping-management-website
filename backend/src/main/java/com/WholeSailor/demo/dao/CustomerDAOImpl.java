package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int register(Customer customer, int new_id) {
        try {
            String SQL_CREATE_CUSTOMER = "INSERT INTO customer(user_id, first_name, last_name, gender, email, phone_no, photo_url) VALUES(?, ?, ?, ?, ?, ?, ?)";
            return jdbc.update(SQL_CREATE_CUSTOMER, new_id, customer.getFirst_name(), customer.getLast_name(), customer.getGender(), customer.getEmail(), customer.getPhone_no(), customer.getPhoto_url());
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;

    }
}
