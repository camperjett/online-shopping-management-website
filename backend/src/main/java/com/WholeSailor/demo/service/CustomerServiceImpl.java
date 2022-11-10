package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.CustomerDAO;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.Customer;
import com.WholeSailor.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    UserService userService;
    @Autowired
    private CustomerDAO customer_dao;
    @Autowired
    private UserDAO user_dao;

    public int register(Customer customer) {
        try {
            User user = new User(customer.getUsername(), customer.getPassword(), customer.getRole());
            int status = this.userService.register(user);
            System.out.println("register" + status);
//            username already
            if (status != 1) return status;
            int user_id = this.user_dao.getByUsername(customer.getUsername()).getId();
            System.out.println("register" + user_id);
            return this.customer_dao.register(customer, user_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
