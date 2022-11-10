package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.OrderDao;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.dao.placeOrder;
import com.WholeSailor.demo.model.Order;
import com.WholeSailor.demo.model.extendedOrder;
import com.WholeSailor.demo.model.productForOrder;
import com.WholeSailor.demo.model.transaction;
import com.WholeSailor.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDAO userDAO;
    

   /* @GetMapping("/status/{x}")
    ResponseEntity<List<Order>> getAllOrders(@PathVariable int x, @RequestHeader("user-id") int user_id) {
        List<Order> orders = this.orderService.getOrderByUser(user_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }*/

    @GetMapping("/order_id/{order_id}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer order_id) {
        Order orders = this.orderService.getOrderById(order_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user_id/{user_id}")
    ResponseEntity<List<extendedOrder>> getOrderByUser(@PathVariable Integer user_id) {
        List<extendedOrder> orders = this.orderService.getOrderByUser(user_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/area_id/{area_id}")
    ResponseEntity<List<Order>> getOrderByArea(@PathVariable Integer area_id) {
        List<Order> orders = this.orderService.getOrderByArea(area_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/update/order_id/{order_id}")
    ResponseEntity<String> updateStatus(@RequestBody Order order, @PathVariable Integer order_id) {
        this.orderService.updateStatus(order, order_id);
        return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
    }


    @GetMapping("/status/{num}/show")
    ResponseEntity<List<extendedOrder>> OrdersStatusFunctions(@PathVariable Integer num, @RequestHeader("user-id") int user_id) {
        //int user_id = 1;
        List<extendedOrder> orders = null;

        if (num == 0) {
            orders = this.orderService.getOrderByUser(user_id);
        } else if (num == 1) {
            orders = this.orderDao.getOrdersByStatus(user_id, 1);
        } else if (num == 2) {
            orders = this.orderDao.getOrdersByStatus(user_id, 2);
        } else if (num == 3) {
            orders = this.orderDao.getOrdersByStatus(user_id, 3);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/search/status/{y}")
    ResponseEntity<List<Order>> SearchByFilter(@RequestHeader("user-id") int user_id, @PathVariable Integer y) {
        List<Order> orders = this.orderDao.SearchFilter(user_id, y);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PutMapping("/{order_id}/cancel")
    ResponseEntity<String> CancelOrder(@PathVariable Integer order_id, @RequestHeader("user-id") int user_id) {
        //int user_id = 0;
        boolean res = orderDao.cancelOrder(order_id, user_id);
        System.out.println(res);
        if (res) {
            return new ResponseEntity<>("Your order has been cancelled", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/{order_id}/details")
    ResponseEntity<List<Object>> OrderDetails(@PathVariable Integer order_id, @RequestHeader("user-id") int user_id) {
        //int user_id = 0;
        List orders = orderDao.orderDetails(order_id, user_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/place")
    ResponseEntity<String> placedByUser(@RequestBody placeOrder order, @RequestHeader("user-id") int user_id) {
        Integer user_role = userDAO.getRole(user_id);
        if(user_role == 0) return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        transaction tr = order.getTran();
        List<productForOrder> Prd = order.getItems();
        System.out.println(order);
        int adid = order.getAd_id();
        int res = orderDao.placeByUser(user_id, Prd, tr, adid);
        if(res == -2) return new ResponseEntity<>("Not enough stock to place order", HttpStatus.BAD_REQUEST);
        System.out.println(res);
        return new ResponseEntity<>("Your order has been placed", HttpStatus.CREATED);
    }

}
