package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.AdminDAO;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.Admin;
import com.WholeSailor.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserService userService;
    @Autowired
    UserDAO userDAO;

    @Autowired
    AdminDAO adminDAO;

    @Override
    public int register(Admin admin) {
        try {
            User user = new User(admin.getUsername(), admin.getPassword(), 3);
            int status = this.userService.register(user);
            System.out.println("register" + status);
//            username already
            if (status != 1) return status;
            int user_id = this.userDAO.getByUsername(admin.getUsername()).getId();
            System.out.println("register" + user_id);
            return this.adminDAO.register(admin, user_id);

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
