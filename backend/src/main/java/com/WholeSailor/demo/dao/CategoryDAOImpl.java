package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Picx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    JdbcTemplate categoryJDBC;

    @Override
    public List<Category> getAll() {
        String query = "SELECT * FROM category";
        List<Category> categories = categoryJDBC.query(query, new BeanPropertyRowMapper<>(Category.class));

        String sql = "SELECT COUNT(*) FROM Product WHERE category_id=?";
        int n = categories.size();
        for (int i = 0; i < n; i++) {
            int category_id = categories.get(i).getCid();
            int number_of_products = categoryJDBC.queryForObject(sql, Integer.class, new Object[]{category_id});
            categories.get(i).setNo_of_products(number_of_products);
            categories.get(i).setImage(getCategoryImages(category_id));
        }
        return categories;
    }

    @Override
    @Transactional
    public Boolean batchCreate(List<Category> list) {
        try {
            String query = "INSERT INTO category (title, image_path) VALUES(?, ?)";
            categoryJDBC.batchUpdate(query, new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, list.get(i).getTitle());
                    ps.setString(2, list.get(i).getImage_path());
                }

                public int getBatchSize() {
                    return list.size();
                }

            });
            String sql2 = "SELECT LAST_INSERT_ID()"; // this gets you primary key of last insertion AFAIK
            int tid = categoryJDBC.queryForObject(sql2, Integer.class);
            addCategoryImages(tid, list.get(0).getImage().getImage());
            return true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public Category getById(int id) {
        String query = "SELECT * FROM category WHERE cid = ?";
        Category res = categoryJDBC.queryForObject(query, new BeanPropertyRowMapper<>(Category.class), id);
        res.setImage(getCategoryImages(id));
        return res;
    }

    @Override
    public List productCount(int cid) {
        String sql = "SELECT count(*) FROM Product WHERE category_id = ?";
        String sql1 = "SELECT * FROM category WHERE cid = ?";
        List res = new ArrayList();
        Integer ans = categoryJDBC.queryForObject(sql, Integer.class, cid);
        Category cat = categoryJDBC.queryForObject(sql1, new BeanPropertyRowMapper<>(Category.class), cid);
        res.add(cat);
        res.add(ans);
        return res;
    }

    @Override
    public boolean editCategory(Category category) {
        try {
            if (category.getTitle() != null) {
                String sql = "update category set title=? where cid=?";
                categoryJDBC.update(sql, new Object[]{category.getTitle(), category.getCid()});
            }
            if (category.getImage_path() != null) {
                String sql = "update category set image_path = ? where cid=?";
                categoryJDBC.update(sql, new Object[]{category.getImage_path(), category.getCid()});
            }
            if (category.getImage() != null) {
                addCategoryImages(category.getCid(), category.getImage().getImage());
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void addCategoryImages(int id, String image) {
        String sq1 = "insert into Cat_pics(product_id, bloby) values(?, ?)";
        byte[] byteContent = image.getBytes();
        Connection con;
        try {
            con = this.categoryJDBC.getDataSource().getConnection();
            Blob blob = con.createBlob();//Where connection is the connection to db object.
            blob.setBytes(1, byteContent);
            this.categoryJDBC.update(sq1, id, blob);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Picx getCategoryImages(int id) {
        try {
            String query = "select * from Cat_pics where product_id = ?";
            Picx res = this.categoryJDBC.queryForObject(query, new BeanPropertyRowMapper<>(Picx.class), id);
            System.out.println(res);
            Blob front = res.getBloby();
            byte[] bdata = front.getBytes(1, (int) front.length());
            String s = new String(bdata);
            res.setImage(s);
            res.setBloby(null);
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
