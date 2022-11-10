package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDAOImpl implements AdminDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int register(Admin admin, int new_id) {
        try {
            String SQL_CREATE_ADMIN = "INSERT INTO admin(user_id, first_name, last_name, gender, email, phone_no, photo_url) VALUES(?, ?, ?, ?, ?, ?, ?)";
            return jdbc.update(SQL_CREATE_ADMIN, new_id, admin.getFirst_name(), admin.getLast_name(), admin.getGender(), admin.getEmail(), admin.getPhone_no(), admin.getPhoto_url());
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public boolean oderStatusAction(List<orderAction> newStat, Integer user_id) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        for (int i = 0; i < newStat.size(); i++) {
            // old status = 2 || 3
            if (newStat.get(i).getOld_status() == 2 || newStat.get(i).getOld_status() == 3) continue;

                // old status = 0
            else if (newStat.get(i).getOld_status() == 0) {
                if (jdbc.update(sql, new Object[]{newStat.get(i).getNew_status(), newStat.get(i).getOrder_id()}) > 0) {
                    if (newStat.get(i).getNew_status() == 3) {
                        String sql1 = "SELECT * FROM order_details WHERE order_id = ?";
                        List<orderDetails> ordDet = jdbc.query(sql1, new BeanPropertyRowMapper<>(orderDetails.class), newStat.get(i).getOrder_id());
                        String sql2 = "UPDATE Product SET stock = stock+? WHERE product_id = ?";
                        for (int j = 0; j < ordDet.size(); j++) {
                            jdbc.update(sql2, ordDet.get(j).getQuantity(), ordDet.get(j).getProduct_id());
                        }
                    }
                }
                else return false;
            }

            // old status = 1
            else {
                if (newStat.get(i).getNew_status() == 1 || newStat.get(i).getNew_status() == 0) continue;
                if (jdbc.update(sql, new Object[]{newStat.get(i).getNew_status(), newStat.get(i).getOrder_id()}) > 0) {
                    if (newStat.get(i).getNew_status() == 3) {
                        String sql1 = "SELECT * FROM order_details WHERE order_id = ?";
                        List<orderDetails> ordDet = jdbc.query(sql1, new BeanPropertyRowMapper<>(orderDetails.class), newStat.get(i).getOrder_id());
                        String sql2 = "UPDATE Product SET stock = stock+? WHERE product_id = ?";
                        for (int j = 0; j < ordDet.size(); j++) {
                            jdbc.update(sql2, ordDet.get(j).getQuantity(), ordDet.get(j).getProduct_id());
                        }
                    }
                }
                else return false;
            }

        }

        return true;

       /* String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        if(jdbc.update(sql, new Object[] {stat, order_id}) > 0)
        {
            if(stat==3)
            {
                String sql1 = "SELECT * FROM order_details WHERE order_id = ?";
                List<orderDetails> ordDet = jdbc.query(sql1, new BeanPropertyRowMapper<>(orderDetails.class), order_id);
                String sql2 = "UPDATE Product SET stock = stock+? WHERE product_id = ?";
                for(int i=0; i<ordDet.size(); i++)
                {
                    jdbc.update(sql2, ordDet.get(i).getQuantity() ,ordDet.get(i).getProduct_id());
                }
            }
            return true;
        }
        return false;*/
    }


    @Override
    public List<Order> getOrders() {
        String sql = "SELECT * FROM orders";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }


   /* @Override
    public int exists(String product_name, int category_id) {
        String sql = "SELECT * FROM Product WHERE product_name = ? AND category_id = ?";
        List<Product> prod = jdbc.query(sql, new BeanPropertyRowMapper<Product>(Product.class), product_name, category_id);
        if (prod.isEmpty()) return 0;
        return 1;
    }


    @Override
    public boolean removeProduct(int product_id) {
        String qry = "DELETE FROM Product WHERE product_id = ?";
        int count = jdbc.update(qry,product_id);
        if(count!=0)
            return true;
        return false;
    }



    @Override
    public boolean addNewProduct(Product product) {
        try {
            String SQL_CREATE_PRODUCT = "INSERT INTO Product(product_name, description, MRP, brand, category_id, stock, date_added) VALUES(?, ?, ?, ?, ?, ?, NOW())";
            int count = jdbc.update(SQL_CREATE_PRODUCT, product.getProduct_name(), product.getDescription(), product.getMRP(), product.getBrand(), product.getCategory_id(), product.getStock());
            if(count!=0)
                return true;
            else
                return false;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    @Override
    public boolean addCategory(Category category) {
        String qry = "INSERT INTO Category(title, image_path) values(?,?)";
        int count = jdbc.update(qry,category.getTitle(), category.getImage_path());
        if(count!=0)
            return true;
        return false;
    }

    @Override
    public boolean removeCategory(int cid) {
        String qry = "DELETE FROM Category WHERE cid =?";
        int count = jdbc.update(qry, cid);
        if(count!=0)
            return true;
        return false;
    }

    */



    @Override
    public List<shopkeeperDetails> getAllShop() {
        String sql = "SELECT * FROM shopkeeper, user WHERE shopkeeper.user_id = user.id";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(shopkeeperDetails.class));
    }

    @Override
    public List<shopkeeperReqDetails> getTentShop() {
        String sql = "SELECT * FROM shopkeeperRequest, user WHERE shopkeeperRequest.user_id = user.id";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(shopkeeperReqDetails.class));
    }

    @Override
    public boolean isAdmin(int user_id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        User usr = jdbc.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user_id);
        if ((usr.getRole()) == 3) return true;
        return false;
    }

    @Override
    public boolean ActionForShopkeeper(Integer shop_reqID, Integer stat) {
        String sql_get = "select isApproved from shopkeeperRequest where sid = ?";
        int previousStatus = jdbc.queryForObject(sql_get, Integer.class, new Object[] {shop_reqID});

        if(previousStatus == 0) {
            if (stat == 0) {
                String sql = "UPDATE shopkeeperRequest SET isApproved = ? WHERE sid = ?";
                if (jdbc.update(sql, new Object[]{stat, shop_reqID}) > 0) return true;
                return false;
            } else if (stat == 1) {
                // create new shopkeeper
                String sql = "UPDATE shopkeeperRequest SET isApproved = ? WHERE sid = ?";
                if (jdbc.update(sql, new Object[]{stat, shop_reqID}) > 0) return true;
                return false;
            } else if (stat == 2) {
                String sql = "UPDATE shopkeeperRequest SET isApproved = ? WHERE sid = ?";
                if (jdbc.update(sql, new Object[]{stat, shop_reqID}) > 0) return true;
                return false;
            }
        }

        return false;
    }
    // 0 - in waiting, 1 - approved, 2 - declined

    @Override
    public List<Feedback> getAllFeedbacks() {
        String sql = "SELECT * FROM feedback";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Feedback.class));
    }


    @Override
    public boolean replyToFeedback(int fid, String rep, int user_id) {
        String qr = "SELECT aid FROM admin WHERE user_id = ?";
        int aid = jdbc.queryForObject(qr, Integer.class, user_id);
        String sql = "UPDATE feedback SET reply = ?, reviewed_by = ? WHERE feed_id = ?";
        if (jdbc.update(sql, new Object[]{rep, aid, fid}) > 0) return true;
        return false;
    }

    @Override
    public int deleteProduct(List<Integer> pids)
    {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        for(int i=0; i< pids.size(); i++)
        {
            if((jdbc.update(sql, pids.get(i))) <= 0) return -1;
        }
        return 1;
    }

}




















