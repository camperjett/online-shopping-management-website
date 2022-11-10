package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Cart;
import com.WholeSailor.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao{
    @Autowired
    JdbcTemplate template;

    @Override
    public Integer insertItemInCart(Cart cart) {
        String sql = "insert into cart(user_id, product_id, quantity) values(?, ?, ?)";
        return template.update(sql, new Object[] {cart.getUser_id(), cart.getProduct_id(), cart.getQuantity()});
    }

    @Override
    public List<Cart> getAllItemsInCart(User user) {
        String sql = "select c.cart_id, c.user_id, c.product_id, c.quantity, p.MRP from cart c, Product p where c.user_id = ? and p.product_id = c.product_id";
        List<Cart> cart_items = template.query(sql, new BeanPropertyRowMapper<>(Cart.class), user.getId());

        return cart_items;
    }

    @Override
    public List<Cart> getItem(Cart cart) {
        String sql = "select * from cart where user_id = ? and product_id=?";
        List<Cart> cart_items = template.query(sql, new BeanPropertyRowMapper<>(Cart.class), new Object[] {cart.getUser_id(), cart.getProduct_id()});
        return cart_items;
    }

    @Override
    public int updateQuantity(Cart cart) {
        String sql = "update cart set quantity = ? where user_id = ? and product_id = ?";
        return template.update(sql, new Object[] {cart.getQuantity(), cart.getUser_id(), cart.getProduct_id()});
    }

    @Override
    public int removeItemFromCart(Cart cart) {
        String sql = "delete from cart where user_id=? and product_id=?";
        return template.update(sql, new Object[] {cart.getUser_id(), cart.getProduct_id()});
    }
}
