package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.OrderDao;
import com.WholeSailor.demo.exceptions.ResourceNotFoundException;
import com.WholeSailor.demo.model.Order;
import com.WholeSailor.demo.model.extendedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public Integer insertOrder(Order order) {
        return this.orderDao.insertOrder(order);
    }

    @Override
    public Order getOrderById(Integer order_id) {
        List<Order> orders = this.orderDao.getOrderById(order_id);

        if (orders.size() == 0)
            throw (new ResourceNotFoundException("Order", "order id", order_id));

        return orders.get(0);
    }

    @Override
    public List<Order> getOrderByArea(Integer area_id) {
        List<Order> orders = this.orderDao.getOrderByArea(area_id);

        if (orders.size() == 0)
            throw (new ResourceNotFoundException("Order", "area id", area_id));

        return orders;
    }

    @Override
    public List<extendedOrder> getOrderByUser(Integer user_id) {
        List<extendedOrder> orders = this.orderDao.getAllOrder(user_id);

        if (orders.size() == 0)
            throw (new ResourceNotFoundException("Order", "user id", user_id));

        return orders;
    }

    @Override
    public List<extendedOrder> getAllOrderAdminByStatus() {

        return this.orderDao.getAllOrderAdminByStatus();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = this.orderDao.getAllOrders();
        return orders;
    }

    public Integer updateStatus(Order order, Integer order_id) {
        List<Order> orders = this.orderDao.getOrderById(order_id);
        if (orders.size() == 0)
            throw (new ResourceNotFoundException("Order", "order id", order_id));

        return this.orderDao.updateOrderStatus(order_id, order);
    }


}
