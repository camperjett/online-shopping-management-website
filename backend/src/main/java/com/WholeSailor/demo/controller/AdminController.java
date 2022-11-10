package com.WholeSailor.demo.controller;


import com.WholeSailor.demo.dao.AdminDAO;
import com.WholeSailor.demo.dao.OrderDao;
import com.WholeSailor.demo.dao.ShopkeeperDAO;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminDAO adminDAO;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDAO userDao;

    @Autowired
    ShopkeeperDAO shopkeeperDAO;

    @Autowired
    ProductService productService;

    @GetMapping("/orders")
    ResponseEntity<List<extendedOrder>> GetAllUserOrders(@RequestHeader("user-id") int user_id) {
        List<extendedOrder> orders = new ArrayList<extendedOrder>();
        if (adminDAO.isAdmin(user_id)) {
            orders = orderDao.getAllOrderAdminByStatus();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/orders/action")
    ResponseEntity<String> OrderStatusAction(@RequestBody List<orderAction> newStat, @RequestHeader("user-id") int user_id) {
        if (adminDAO.isAdmin(user_id)) {
            boolean res = adminDAO.oderStatusAction(newStat, user_id);
            if (res) {
                return new ResponseEntity<>("Status entered by admin", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_GATEWAY);
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/orders/{order_id}/delete")
    ResponseEntity<String> DeleteOrder(@PathVariable Integer order_id, @RequestHeader("user-id") int user_id) {
        if (adminDAO.isAdmin(user_id)) {
            boolean res = orderDao.deleteOrder(order_id);
            if (res) {
                return new ResponseEntity<>("Your order has been deleted", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_GATEWAY);
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }
    }


    // For Request by existing user to become a shopkeeper

    @GetMapping("/shopkeepers")
    ResponseEntity<List<shopkeeperDetails>> GetAllShopkeepers(@RequestHeader("user-id") int user_id) {
        List<shopkeeperDetails> shop = new ArrayList<shopkeeperDetails>();
        boolean x = adminDAO.isAdmin(user_id);
        if (x) {
            shop = adminDAO.getAllShop();
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(shop, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/shopkeepers/tentative")
    ResponseEntity<List<shopkeeperReqDetails>> GetAllTentativeShopkeepers(@RequestHeader("user-id") int user_id) {
        List<shopkeeperReqDetails> req = new ArrayList<shopkeeperReqDetails>();
        if (adminDAO.isAdmin(user_id)) {
            req = adminDAO.getTentShop();
            return new ResponseEntity<>(req, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(req, HttpStatus.BAD_REQUEST);
        }
    }

   /* @PutMapping("/shopkeepers/{shop_reqID}/action/{stat}")
    ResponseEntity<String> ActionOnShopkeeper(@PathVariable Integer stat, @PathVariable Integer shop_reqID, @RequestHeader("user-id") int user_id) {
        if (adminDAO.isAdmin(user_id)) {
            boolean res = adminDAO.ActionForShopkeeper(shop_reqID, stat);
            if (res) {
                shopkeeper_request shop_req = shopkeeperDAO.getShopReqByID(shop_reqID);
                if (stat == 1) {
                    shopkeeperDAO.createNewShop(shop_req);
                }
                return new ResponseEntity<>("Action completed successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
            }
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }
    }*/

    @PutMapping("/shopkeepers/action")
    ResponseEntity<String> ActionOnShopkeeper(@RequestBody List<ShopkeeperAction> requests, @RequestHeader("user-id") int user_id) {
        Integer shop_reqID;
        Integer stat;
        if (adminDAO.isAdmin(user_id)) {
            int n = requests.size();

            for (int i = 0; i < n; i++) {
                shop_reqID = requests.get(i).getRequestId();
                stat = requests.get(i).getStatus();

                boolean res = adminDAO.ActionForShopkeeper(shop_reqID, stat);
                if (res) {
                    shopkeeper_request shop_req = shopkeeperDAO.getShopReqByID(shop_reqID);
                    if (stat == 1) {
                        shopkeeperDAO.createNewShop(shop_req);
                    }
                }
            }

            return new ResponseEntity<>("Action completed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }


    }


    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(this.userDao.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/users/remove/{user_id}")
    ResponseEntity<String> removeUser(@PathVariable int user_id) {
        return new ResponseEntity<>(this.userDao.removeUser(user_id), HttpStatus.OK);
    }


    // FEEDBACK APIS

    @GetMapping("/feedback")
    ResponseEntity<List<Feedback>> GetAllFeedbacks(@RequestHeader("user-id") int user_id) {
        List<Feedback> fdb = new ArrayList<>();
        if (adminDAO.isAdmin(user_id)) {
            fdb = adminDAO.getAllFeedbacks();
            return new ResponseEntity<>(fdb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(fdb, HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/feedback/reply/{fid}")
    ResponseEntity<String> ReplyToFeedbacks(@RequestHeader("user-id") int user_id, @PathVariable int fid, @RequestBody String rep) {
        if (adminDAO.isAdmin(user_id)) {
            boolean ans = adminDAO.replyToFeedback(fid, rep, user_id);
            if (ans) {
                return new ResponseEntity<>("Your reply has been saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
            }
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }

    }

    // END OF FEEDBACK OF APIS

    // for deleting products
    @PostMapping("/deleteProduct")
    ResponseEntity<String> DeleteProduct(@RequestHeader("user-id") int user_id, @RequestBody List<Integer> pids) {
        if (adminDAO.isAdmin(user_id)) {
            int res = adminDAO.deleteProduct(pids);
            if (res == 1) {
                return new ResponseEntity<>("Products have been deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
            }
        } else {
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/edit/{product_id}")
    ResponseEntity<String> updateProduct(@PathVariable int product_id, @RequestHeader("user-id") int user_id, @RequestBody Product product) {
        product.setProduct_id(product_id);
        String result = productService.updateProduct(product, user_id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}

