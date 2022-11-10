package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Product;

import java.util.List;

public interface WishlistDAO {

    List<Product> getAllinWish(int user_id);

    int addNewWish(int user_id, int product_id);

    int removeWish(int user_id, int product_id);
}
