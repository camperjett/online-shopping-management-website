package com.WholeSailor.demo.service;

import com.WholeSailor.demo.model.Product;
import com.WholeSailor.demo.payload.SearchProductDTO;

import java.util.List;

public interface ProductService {
    public int createNew(Product product);

    public Product getByProductName(String product_name);

    public Product getByProductID(int product_id);

    public List<Product> getAllProducts();

    public List<Product> getByProductCID(int category_id);

    List<Product> getAllProductsCustom(SearchProductDTO product);

    String updateProduct(Product product, int user_id);
}
