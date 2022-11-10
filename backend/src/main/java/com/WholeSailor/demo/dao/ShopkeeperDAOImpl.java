package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Shopkeeper;
import com.WholeSailor.demo.model.shopkeeperReqDetails;
import com.WholeSailor.demo.model.shopkeeper_request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopkeeperDAOImpl implements ShopkeeperDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int register(Shopkeeper shopkeeper, int new_id) {
        try {
            String SQL_CREATE_CUSTOMER = "INSERT INTO shopkeeper(user_id, first_name, last_name, gender, email, phone_no, photo_url, license_image_url, license_no) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return jdbc.update(SQL_CREATE_CUSTOMER, new_id, shopkeeper.getFirst_name(), shopkeeper.getLast_name(), shopkeeper.getGender(), shopkeeper.getEmail(), shopkeeper.getPhone_no(), shopkeeper.getPhoto_url(), shopkeeper.getLicense_image_url(), shopkeeper.getLicense_no());
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public boolean existInShop(int user_id) {
        String sql = "SELECT * FROM shopkeeper WHERE user_id = ?";
        List<Shopkeeper> sh = jdbc.query(sql, new BeanPropertyRowMapper<Shopkeeper>(Shopkeeper.class), user_id);
        if (sh.isEmpty()) return false;
        return true;
    }

    @Override
    public boolean createNewReq(shopkeeper_request shop_req, int user_id) {
        String sql = "INSERT INTO shopkeeperRequest(user_id, first_name, last_name, gender, email, phone_no, photo_url, license_image_url, license_no, isApproved) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if((jdbc.update(sql, user_id, shop_req.getFirst_name(), shop_req.getLast_name(), shop_req.getGender(), shop_req.getEmail(), shop_req.getPhone_no(), shop_req.getPhoto_url(), shop_req.getLicense_image_url(), shop_req.getLicense_no(), 0)) > 0) return true;
        return false;
    }

    @Override
    public boolean existInShopReq(int user_id){
        String sql = "SELECT * FROM shopkeeperRequest WHERE user_id = ?";
        List<Shopkeeper> sh = jdbc.query(sql, new BeanPropertyRowMapper<Shopkeeper>(Shopkeeper.class), user_id);
        if (sh.isEmpty()) return false;
        return true;
    }

    @Override
    public shopkeeper_request getShopReqByID(int shop_reqID)
    {
        String sql = "SELECT * FROM shopkeeperRequest WHERE sid = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<shopkeeper_request>(shopkeeper_request.class), shop_reqID);
    }

    @Override
    public void createNewShop(shopkeeper_request shop_req)
    {
        String sql = "SELECT * FROM shopkeeperRequest, user WHERE shopkeeperRequest.user_id = user.id AND sid = ?";
        shopkeeperReqDetails srd = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(shopkeeperReqDetails.class), shop_req.getSid());
        int userID = srd.getUser_id();
        String qr = "INSERT INTO shopkeeper(sid, user_id, first_name, last_name, gender, email, phone_no, photo_url, license_image_url, license_no) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(qr, shop_req.getSid(), userID, shop_req.getFirst_name(), shop_req.getLast_name(), shop_req.getGender(), shop_req.getEmail(), shop_req.getPhone_no(), shop_req.getPhoto_url(), shop_req.getLicense_image_url(), shop_req.getLicense_no());
    }

}
