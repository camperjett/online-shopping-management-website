package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Order;
import com.WholeSailor.demo.model.extendedOrder;
import com.WholeSailor.demo.model.productForOrder;
import com.WholeSailor.demo.model.transaction;

import java.util.List;

public interface OrderDao {
    //create
    Integer insertOrder(Order order);

    //Get order
    List<extendedOrder> getAllOrder(Integer user_id);

    List<Order> getOrderById(Integer order_id);

    List<Order> getOrderByArea(Integer area_id);

    //update
    Integer updateOrderStatus(Integer order_id, Order order);

    List<Order> getOrder(Order order);

    List<Order> getAllOrders();


    List<extendedOrder> getOrdersByStatus(Integer user_id, int stat);

    List<extendedOrder> getAllOrderAdminByStatus();

    boolean cancelOrder(Integer order_id, Integer user_id);

    boolean deleteOrder(Integer order_id);

    List orderDetails(Integer order_id, Integer user_id);

    List<Order> SearchFilter(Integer x, Integer y);

    int placeByUser(int user_id, List<productForOrder> Prod, transaction tran, int ad_id);
}
