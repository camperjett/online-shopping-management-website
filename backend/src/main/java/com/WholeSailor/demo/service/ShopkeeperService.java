package com.WholeSailor.demo.service;

import com.WholeSailor.demo.model.ShopkeeperRegister;
import com.WholeSailor.demo.model.shopkeeper_request;

public interface ShopkeeperService {

    //public int register(Shopkeeper shopkeeper);
    
    public int createNewShop(shopkeeper_request shop_req, int user_id);

    public int register(ShopkeeperRegister shopkeeper);
}
