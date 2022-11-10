package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.payload.SearchProductDTO;

import java.util.List;

public interface ProductDAO {
    public Product getByName(String name);

    public Product getByID(Integer pid);

    public List<Product> getAll();

    public List<Product> getByCID(Integer cid);

    public List<Product> getByCIDFilter(Integer cid, CatProductFilter filter);

    public int exists(String product_name, int category_id);

    public void create(Product prod);

    public int delete(int id);

    public int addQuestion(int product_id, product_faq faq);

    public int removeQuestion(int id, int qid);

    public int giveAnswer(int id, int qid, product_faq faq);

    public int ReviewAdd(int id, Review review, int user_id);

    public int removeReview(int id, int rid);


    public int rating(int id, int rid, Review ans);

    List<Product> getAllProductCustom(SearchProductDTO product);

    public Product productDetail(int id);

    public int votingFAQ(int qid, Integer vote);

    public int votingREVIEW(int rid, Integer vote);


    void updateProductName(Product product);

    void updateProductRating(Product product);

    void updateProductStock(Product product);

    void updateProductDescription(Product product);

    void updateProductMRP(Product product);

    void updateProductBrand(Product product);

    void updateProductCategory(Product product);

    void updatePhotoURL(Product product);

    void updateProductImages(Product product);
}

