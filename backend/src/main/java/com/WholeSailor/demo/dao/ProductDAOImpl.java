package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.payload.SearchProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    JdbcTemplate template;

    @Override
    public Product getByName(String name) {
        String sql = "SELECT * FROM Product WHERE product_name = ?";
        List<Product> prod = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class), name);
        if (prod.isEmpty()) throw new RuntimeException("Product doesn't exist");
        return template.queryForObject(sql, new BeanPropertyRowMapper<Product>(Product.class), name);
    }

    @Override
    public Product getByID(Integer pid) {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        List<Product> prod = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class), pid);
        if (prod.isEmpty()) throw new RuntimeException("Product doesn't exist");
        Product res = template.queryForObject(sql, new BeanPropertyRowMapper<Product>(Product.class), pid);
        res.setProduct_images(getProductImages(res.getProduct_id()));
        return res;
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM Product ORDER";
        List<Product> prod = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class));
        if (prod.isEmpty()) throw new RuntimeException("No products in database");
        return template.query(sql, new BeanPropertyRowMapper<Product>(Product.class));
    }

    @Override
    public List<Product> getByCID(Integer cid) {
        String query1 = "SELECT * FROM Product WHERE category_id = ?";
        return template.query(query1, new BeanPropertyRowMapper<Product>(Product.class), cid);

    }

    @Override
    public List<Product> getByCIDFilter(Integer cid, CatProductFilter filter) {
        System.out.println(filter);
        String query1 = "SELECT * FROM Product WHERE category_id = ?";
        return template.query(query1, new BeanPropertyRowMapper<Product>(Product.class), cid);
    }

    @Override
    public int exists(String product_name, int category_id) {
        String sql = "SELECT * FROM Product WHERE product_name = ? AND category_id = ?";
        List<Product> prod = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class), product_name, category_id);
        if (prod.isEmpty()) return 0;
        return 1;
    }

    @Override
    public void create(Product prod) {
        try {
            String SQL_CREATE_PRODUCT = "INSERT INTO Product(product_name, description, MRP, brand, category_id, stock, date_added, photo_url) VALUES(?, ?, ?, ?, ?, ?, NOW(), ?)";
            template.update(SQL_CREATE_PRODUCT, prod.getProduct_name(), prod.getDescription(), prod.getMRP(), prod.getBrand(), prod.getCategory_id(), prod.getStock(), prod.getPhoto_url());

            String sql2 = "SELECT LAST_INSERT_ID()"; // this gets you primary key of last insertion AFAIK
            int tid = template.queryForObject(sql2, Integer.class);

            for (int i = 0; i < prod.product_images.size(); i++) {
                String img = prod.getProduct_images().get(i).getImage();
                byte[] byteContent = img.getBytes();
                Connection con;
                String SQL_INSERT_IMG = "INSERT INTO Product_pics(product_id, bloby) VALUES(?, ?)";
                try {
                    con = this.template.getDataSource().getConnection();
                    Blob blob = con.createBlob();//Where connection is the connection to db object.
                    blob.setBytes(1, byteContent);
                    this.template.update(SQL_INSERT_IMG, tid, blob);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM Product WHERE product_id=?";
        return template.update(query, id);
    }

    @Override
    public int addQuestion(int product_id, product_faq faq) {
        try {
            String qry = "insert into product_faq(product_id,question_description, upVotes, downVotes) values(?,?,?,?)";
            int count = template.update(qry, product_id, faq.getQuestion_description(), faq.getUpVotes(), faq.getDownVotes());
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    public int removeQuestion(int id, int qid) {
        try {
            String qry = "delete from product_faq where product_id =? and question_id = ?";
            int count = template.update(qry, id, qid);
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int giveAnswer(int id, int qid, product_faq faq) {
        try {
            String qry = "update product_faq set answer = ? where product_id = ? and question_id = ?";
            int count = template.update(qry, faq.getAnswer(), id, qid);
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int ReviewAdd(int id, Review review, int user_id) {
        // int user_id = 0;
        try {
            String qry = "insert into Review(user_id,product_id, title, text, upVotes, downVotes) values(?,?,?,?,?,?)";
            int count = template.update(qry, user_id, id, review.getTitle(), review.getText(), review.getUpVotes(), review.getDownVotes());
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int rating(int id, int rid, Review review) {
        try {
            String qry = "update Review set rating = ? where product_id = ? and review_id= ?";
            int count = template.update(qry, review.getRating(), id, rid);
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override

    public List<Product> getAllProductCustom(SearchProductDTO product) {
        try {
            int w = product.getCategory_id();
            int z = product.getPrice_high();
            int a = product.getPrice_low();
            int b = product.getRating_high();
            int c = product.getRating_low();

            List<String> brands = product.getBrands();
            int total_brands_in_filter = 0;
            if (brands != null)
                total_brands_in_filter = brands.size();

            String sql = null;
            List<Object> parameters_list = new ArrayList<>();
            parameters_list.addAll(Arrays.asList(w, w));
            if (brands == null) {
                sql = String.format("select * from Product p where  (category_id = ? or ?=0) and (p.MRP<? or ? = 0) and (p.MRP>? or ?=0) and (avg_rating<? or ?=0) and (avg_rating>? or ?=0)");

            } else {
                String inSql = String.join(",", Collections.nCopies(total_brands_in_filter, "?"));
                sql = String.format("select * from Product p where  (category_id = ? or ?=0) and (brand in (%s)) and (p.MRP<? or ? = 0) and (p.MRP>? or ?=0) and (avg_rating<? or ?=0) and (avg_rating>? or ?=0)", inSql);
                parameters_list.addAll(brands);
            }
            parameters_list.addAll(Arrays.asList(z, z, a, a, b, b, c, c));

            List<Product> res = this.template.query(sql, new BeanPropertyRowMapper<>(Product.class), parameters_list.toArray());
            for (int i = 0; i < res.size(); i++) {
                res.get(i).setProduct_images(getProductImages(res.get(i).getProduct_id()));
            }
//            System.out.println(res);
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Picx> getProductImages(int id) {
        try {
            String query = "SELECT * FROM Product_pics WHERE product_id = ?";
            List<Picx> pics = this.template.query(query, new BeanPropertyRowMapper<>(Picx.class), id);
            for (int i = 0; i < pics.size(); i++) {
                Blob front = pics.get(i).getBloby();
                byte[] bdata = front.getBytes(1, (int) front.length());
                String s = new String(bdata);
                pics.get(i).setImage(s);
                pics.get(i).setBloby(null);
            }
            return pics;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Product productDetail(int id) {
        Product result = new Product();
        String qry = "select * from Product where product_id=?";
        result = template.queryForObject(qry, new BeanPropertyRowMapper<>(Product.class), id);
        String qry2 = "select * from Review where product_id=?";
        List<Review> ans2 = template.query(qry, new BeanPropertyRowMapper<>(Review.class), id);
        String qry3 = "select * from product_faq where product_id=?";
        List<product_faq> ans3 = template.query(qry, new BeanPropertyRowMapper<>(product_faq.class), id);
        result.setReviews(ans2);
        result.setProductFaqs(ans3);
        return result;

    }


    public int removeReview(int id, int rid) {
        try {
            String qry = "delete from review where product_id = ? and review_id =? ";
            int count = template.update(qry, id, rid);
            if (count != 0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    @Override
    public int votingFAQ(int qid, Integer vote) {
        try {
            if (vote == 0) {
                String qry = "update product_faq set downVotes = downVotes+1 where question_id= ?";
                int count = template.update(qry, qid);
                if (count != 0)
                    return 1;
                else
                    return 0;

            } else if (vote == 1) {
                String qry = "update product_faq set upVotes = upVotes+1 where question_id= ?";
                int count = template.update(qry, qid);
                if (count != 0)
                    return 1;
                else
                    return 0;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int votingREVIEW(int rid, Integer vote) {
        try {
            if (vote == 0) {
                String qry = "update Review set downVotes = downVotes+1 where review_id= ?";
                int count = template.update(qry, rid);
                if (count != 0)
                    return 1;
                else
                    return 0;

            } else if (vote == 1) {
                String qry = "update Review set upVotes = upVotes+1 where review_id= ?";
                int count = template.update(qry, rid);
                if (count != 0)
                    return 1;
                else
                    return 0;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    @Override
    public void updateProductName(Product product) {
        String sql = "update Product set product_name=? where product_id=?";
        this.template.update(sql, new Object[]{product.getProduct_name(), product.getProduct_id()});
    }

    @Override
    public void updateProductRating(Product product) {
        String sql = "update Product set avg_rating=? where product_id=?";
        this.template.update(sql, new Object[]{product.getAvg_rating(), product.getProduct_id()});
    }

    @Override
    public void updateProductStock(Product product) {
        String sql = "update Product set stock=? where product_id=?";
        this.template.update(sql, new Object[]{product.getStock(), product.getProduct_id()});
    }

    @Override
    public void updateProductDescription(Product product) {
        String sql = "update Product set description=? where product_id=?";
        this.template.update(sql, new Object[]{product.getDescription(), product.getProduct_id()});
    }

    @Override
    public void updateProductMRP(Product product) {
        String sql = "update Product set MRP=? where product_id=?";
        this.template.update(sql, new Object[]{product.getMRP(), product.getProduct_id()});
    }

    @Override
    public void updateProductBrand(Product product) {
        String sql = "update Product set brand=? where product_id=?";
        this.template.update(sql, new Object[]{product.getBrand(), product.getProduct_id()});
    }

    @Override
    public void updateProductCategory(Product product) {
        String sql = "update Product set category_id=? where product_id=?";
        this.template.update(sql, new Object[]{product.getCategory_id(), product.getProduct_id()});
    }

    @Override
    public void updatePhotoURL(Product product) {
        String sql = "update Product set photo_url=? where product_id=?";
        this.template.update(sql, new Object[]{product.getPhoto_url(), product.getProduct_id()});
    }

    @Override
    public void updateProductImages(Product product) {
        try {
            for (int i = 0; i < product.product_images.size(); i++) {
                String img = product.getProduct_images().get(i).getImage();
                byte[] byteContent = img.getBytes();
                Connection con;
                String SQL_INSERT_IMG = "INSERT INTO Product_pics(product_id, bloby) VALUES(?, ?)";
                try {
                    con = this.template.getDataSource().getConnection();
                    Blob blob = con.createBlob();//Where connection is the connection to db object.
                    blob.setBytes(1, byteContent);
                    this.template.update(SQL_INSERT_IMG, product.getProduct_id(), blob);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
