package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Product;

import java.util.List;

public interface SearchDAO {
    List<Product> searchProductByName(String keyword);

    List<Category> searchCategories(String keyword);

    List<Product> searchInSomeCategory(String keyword, String someCategory);

    List<Product> searchByAvail(int val);

    List<Product> searchByRat(List<Integer> range);

    List<Product> searchByPrice(List<Integer> range);
}















