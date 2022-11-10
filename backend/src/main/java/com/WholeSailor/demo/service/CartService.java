package com.WholeSailor.demo.service;

import com.WholeSailor.demo.model.Cart;
import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.payload.CartDto;

import java.util.List;

public interface CartService {
    String insertItemInCart(Cart cart);
    CartDto getAllItemsInCart(User user);
    String updateQuantity(Cart cart);
    String removeItemFromCart(Cart cart);
}
