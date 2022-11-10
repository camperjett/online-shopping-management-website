package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistDAOImpl implements WishlistDAO{

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Product> getAllinWish(int user_id)
    {
        String sql = "SELECT product_id FROM wishlist WHERE user_id = ?";
        List<Integer> pid = template.queryForList(sql, Integer.class, user_id);
        List<Product> wish = new ArrayList<>();
        String sql1 = "SELECT * FROM Product WHERE product_id = ?";
        for(int i=0; i<pid.size(); i++)
        {
            Product temp = template.queryForObject(sql1, new BeanPropertyRowMapper<>(Product.class), pid.get(i));
            wish.add(temp);
        }

        return wish;
    }


    @Override
    public int addNewWish(int user_id, int product_id)
    {
        String sql = "SELECT product_id FROM wishlist WHERE user_id = ? AND product_id = ?";
        List<Integer> wish = template.queryForList(sql, Integer.class, user_id, product_id);
        System.out.println(wish);
        if(wish.size() != 0) return -1;
        String sql1 = "INSERT INTO wishlist(user_id, product_id) VALUES(?, ?)";
        if((template.update(sql1, user_id, product_id)) > 0) return 1;
        return 0;
    }

    @Override
    public int removeWish(int user_id, int product_id)
    {
        String sql = "SELECT product_id FROM wishlist WHERE user_id = ? AND product_id = ?";
        List<Integer> wish = template.queryForList(sql, Integer.class, user_id, product_id);
        System.out.println(wish);
        if(wish.size() == 0) return -1;
        String sql1 = "DELETE FROM wishlist WHERE user_id = ? AND product_id = ?";
        if((template.update(sql1, user_id, product_id)) > 0) return 1;
        return 0;
    }


}
