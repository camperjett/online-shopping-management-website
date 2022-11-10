package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Picx;

import java.util.List;

public interface CategoryDAO {
    public List<Category> getAll();

    public Boolean batchCreate(List<Category> list);

    public Category getById(int id);

    public List productCount(int cid);

    boolean editCategory(Category category);

    public void addCategoryImages(int id, String images);

    public Picx getCategoryImages(int id);
}
