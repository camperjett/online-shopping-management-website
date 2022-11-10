package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.ShopkeeperDAO;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.ShopkeeperRegister;
import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.model.shopkeeper_request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopkeeperServiceImpl implements ShopkeeperService {
    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;
    @Autowired
    ShopkeeperDAO shopkeeperDAO;


    @Override
    public int createNewShop(shopkeeper_request shop_req, int user_id) {
        try {
            if (shopkeeperDAO.existInShop(user_id)) return -2;
            else if (shopkeeperDAO.existInShopReq(user_id)) return -3;
            else {
                if (shopkeeperDAO.createNewReq(shop_req, user_id)) return 1;
                else {
                    return 0;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    /*@Override
    public int register(Shopkeeper shopkeeper) {
        try {
            User user = new User(shopkeeper.getUsername(), shopkeeper.getPassword(), 1);
            int status = this.userService.register(user);
            System.out.println("register " + status);
//            username already
            if (status != 1) return status;
            int user_id = this.userDAO.getByUsername(shopkeeper.getUsername()).getId();
            System.out.println("registered user_id: " + user_id);
            return this.shopkeeperDAO.register(shopkeeper, user_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }*/

    @Override
    public int register(ShopkeeperRegister shopkeeper) {
        try {
            User user = new User(shopkeeper.getUsername(), shopkeeper.getPassword(), 0);
            int status = this.userService.register(user);
            System.out.println("register " + status);
//            username already
            if (status != 1) return status;
            int user_id = this.userDAO.getByUsername(shopkeeper.getUsername()).getId();
            System.out.println("registered user_id: " + user_id);
            shopkeeper_request shop_req = new shopkeeper_request();
            shop_req.setSid(shopkeeper.getSid());
            shop_req.setFirst_name(shopkeeper.getFirst_name());
            shop_req.setLast_name(shopkeeper.getLast_name());
            shop_req.setGender(shopkeeper.getGender());
            shop_req.setEmail(shopkeeper.getEmail());
            shop_req.setPhone_no(shopkeeper.getPhone_no());
            shop_req.setPhoto_url(shopkeeper.getPhoto_url());
            shop_req.setAddresses(shopkeeper.getAddresses());
            shop_req.setLicense_image_url(shopkeeper.getLicense_image_url());
            shop_req.setLicense_no(shopkeeper.getLicense_no());
            shop_req.setIsApproved(0);
            return this.createNewShop(shop_req, user_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
