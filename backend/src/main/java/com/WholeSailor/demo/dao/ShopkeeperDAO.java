package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Shopkeeper;
import com.WholeSailor.demo.model.shopkeeper_request;

public interface ShopkeeperDAO {
    public boolean createNewReq(shopkeeper_request shop_req, int user_id);

    public boolean existInShopReq(int user_id);

    public boolean existInShop(int user_id);

    public int register(Shopkeeper shopkeeper, int new_id);

    public shopkeeper_request getShopReqByID(int shop_reqID);

    public void createNewShop(shopkeeper_request shop_req);
}
