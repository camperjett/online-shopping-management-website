package com.WholeSailor.demo.service;

import com.WholeSailor.demo.model.Order;
import com.WholeSailor.demo.model.extendedOrder;

import java.util.List;

public interface OrderService {
    Integer insertOrder(Order order);

    Order getOrderById(Integer order_id);

    List<Order> getOrderByArea(Integer area_id);

    List<extendedOrder> getOrderByUser(Integer user_id);

    List<extendedOrder> getAllOrderAdminByStatus();

    List<Order> getAllOrders();

    Integer updateStatus(Order order, Integer order_id);
}
