package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.*;


import java.util.List;

public interface AdminDAO {
    public boolean ActionForShopkeeper(Integer shop_reqID, Integer stat);

    public boolean isAdmin(int user_id);

    public int register(Admin admin, int new_id);

    boolean oderStatusAction(List<orderAction> newStat, Integer user_id);

    List<Order> getOrders();


    /*public boolean addNewProduct(Product product);

    public int exists(String product_name, int category_id);

    public boolean removeProduct(int product_id);

    public boolean addCategory(Category category);

    public boolean removeCategory(int cid);*/

    List<shopkeeperDetails> getAllShop();

    List<shopkeeperReqDetails> getTentShop();

    List<Feedback> getAllFeedbacks();

    boolean replyToFeedback(int fid, String rep, int user_id);

    int deleteProduct(List<Integer> pids);
}
