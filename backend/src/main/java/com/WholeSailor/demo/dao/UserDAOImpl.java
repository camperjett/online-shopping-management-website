package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate userjdbc;

    @Override
    public int create(User user) {
        try {
            System.out.println("in user create dao");
            String SQL_CREATE_USER = "INSERT INTO user (username, password, role, date_joined) VALUES (?, ?, ?, NOW())";
            return userjdbc.update(SQL_CREATE_USER, user.getUsername(), user.getPassword(), user.getRole());
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        try {

            String SQL_GET_BY_USERNAME = "SELECT * FROM user WHERE username=?";
            return userjdbc.queryForObject(SQL_GET_BY_USERNAME, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            System.out.println("error in get by username" + e);
        }
        return null;
    }

    @Override
    public Boolean exists(String username) {
        String query = "SELECT * FROM user WHERE username=?";
        try {
            List<User> result = userjdbc.query(query, new BeanPropertyRowMapper<User>(User.class), username);
            System.out.println("check exists: " + result);
            return !result.isEmpty();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    @Override
    public Boolean match(User user) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ? AND role = ?";
        try {
            User result = userjdbc.queryForObject(query, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword(), user.getRole());
            return !(result == null);
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public List<UserDto> getAll() {
        String sql_customers = "select u.id, u.username, u.role, c.first_name, c.last_name from user u, customer c where u.id = c.user_id";
        String sql_shopkeepers = "select u.id, u.username, u.role, s.first_name, s.last_name from user u, shopkeeper s where u.id = s.user_id";
        List<UserDto> customers = userjdbc.query(sql_customers, new BeanPropertyRowMapper<>(UserDto.class));
        List<UserDto> shopkeepers = userjdbc.query(sql_shopkeepers, new BeanPropertyRowMapper<>(UserDto.class));

        List<UserDto> users = new ArrayList<UserDto>();
        users.addAll(customers);
        users.addAll(shopkeepers);

        return users;

    }

    @Override
    public int updateById(User user, int id) {
        return 0;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public String removeUser(int user_id) {
        try{
            String sql = "delete from user where id=?";
            this.userjdbc.update(sql, user_id);
            return "User deleted";
        }
        catch (Exception e){
            System.out.println(e);
        }

        return "Some error occured";
    }

    @Override
    public Integer getRole(int user_id)
    {
        String sql = "SELECT role FROM user WHERE id = ?";
        return userjdbc.queryForObject(sql, Integer.class, user_id);
    }

}
