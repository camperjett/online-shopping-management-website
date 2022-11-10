package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO user_dao;

    @Override
    public int register(User user) {
        try {
            if (this.user_dao.exists(user.getUsername())) {
                return -1;
            }
            return this.user_dao.create(user);
        } catch (Exception e) {
            System.out.println(e);
//            throw new Exception("User does not exists");
        }
        return 0;
    }
}
