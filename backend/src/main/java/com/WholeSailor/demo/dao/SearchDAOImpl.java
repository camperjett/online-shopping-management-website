package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchDAOImpl implements SearchDAO{

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Product> searchProductByName(String keyword)
    {
        String sql = "SELECT * FROM Product WHERE product_name LIKE %?%";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), keyword);
    }

    @Override
    public List<Category> searchCategories(String keyword)
    {
        String sql = "SELECT * FROM category WHERE title LIKE %?%";
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class), keyword);
    }

    @Override
    public List<Product> searchInSomeCategory(String keyword, String someCategory)
    {
        String sql = "SELECT cid FROM category WHERE title = ?";
        int cid = template.queryForObject(sql, Integer.class);
        String sql1 = "SELECT * FROM Product WHERE product_name LIKE %?% AND category_id = ?";
        return template.query(sql1, new BeanPropertyRowMapper<>(Product.class), keyword, cid);
    }


    @Override
    public List<Product> searchByAvail(int val)
    {
        String sql = "SELECT * FROM Product WHERE availability = ?";
        if(val == 1)
            return template.query(sql, new BeanPropertyRowMapper<>(Product.class), true);
        else
            return template.query(sql, new BeanPropertyRowMapper<>(Product.class), false);
    }


    @Override
    public List<Product> searchByRat(List<Integer> range)
    {
        String sql = "SELECT * FROM Product WHERE avg_rating BETWEEN ? AND ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), range.get(0), range.get(1));
    }

    @Override
    public List<Product> searchByPrice(List<Integer> range)
    {
        String sql = "SELECT * FROM Product WHERE MRP BETWEEN ? AND ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), range.get(0), range.get(1));
    }


}






















