package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Customer;

public interface CustomerDAO {
    public int register(Customer customer, int new_id);
}
