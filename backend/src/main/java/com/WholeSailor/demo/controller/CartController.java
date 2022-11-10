package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.model.Cart;
import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.payload.CartDto;
import com.WholeSailor.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    //insert
    @PostMapping("/add/{product_id}")
    ResponseEntity<String> addToCart(@RequestBody Cart cart, @PathVariable Integer product_id, @RequestHeader("user-id") int user_id){
        cart.setProduct_id(product_id);
        cart.setUser_id(user_id);
        String response = this.cartService.insertItemInCart(cart);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/update/{product_id}")
    ResponseEntity<String> updateQuantity(@RequestBody Cart cart, @PathVariable Integer product_id,@RequestHeader("user-id") int user_id){
        cart.setUser_id(user_id);
        cart.setProduct_id(product_id);
        String response =this.cartService.updateQuantity(cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //remove
    @DeleteMapping("/remove/{product_id}")
    ResponseEntity<String> removeFromCart(@PathVariable Integer product_id, @RequestHeader("user-id") int user_id){
        Cart cart = new Cart();
        cart.setProduct_id(product_id);
        cart.setUser_id(user_id);
        String response = this.cartService.removeItemFromCart(cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //get all
    @GetMapping("")
    ResponseEntity<CartDto> getAllItems(@RequestHeader("user-id") int user_id){
        User user = new User();
        user.setId(user_id);
        //List<Cart> cart_items = this.cartService.getAllItemsInCart(user);
        CartDto cart = this.cartService.getAllItemsInCart(user);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
