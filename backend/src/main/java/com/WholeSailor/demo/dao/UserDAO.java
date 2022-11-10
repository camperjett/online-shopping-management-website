package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.model.UserDto;

import java.util.List;

public interface UserDAO {
    int create(User user);

    User getById(int id);

    User getByUsername(String username);
    Boolean exists(String username);

    Boolean match(User user);

    List<UserDto> getAll();

    int updateById(User user, int id);

    int deleteById(int id);

    int deleteAll();

    String removeUser(int user_id);

    Integer getRole(int user_id);
}
