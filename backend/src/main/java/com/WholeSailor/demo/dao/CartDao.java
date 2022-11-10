package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Cart;
import com.WholeSailor.demo.model.User;

import java.util.List;

public interface CartDao {
    //create
    Integer insertItemInCart(Cart cart);
    //retrieve
    List<Cart> getAllItemsInCart(User user);
    List<Cart> getItem(Cart cart);
    //update
    int updateQuantity(Cart cart);
    //delete
    int removeItemFromCart(Cart cart);
}
