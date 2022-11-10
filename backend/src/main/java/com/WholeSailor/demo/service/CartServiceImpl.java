package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.CartDao;
import com.WholeSailor.demo.model.Cart;
import com.WholeSailor.demo.model.User;
import com.WholeSailor.demo.payload.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*Doubt#1: Do we need to check the foreign key constraints i.e. whether the referenced field values exist or not?*/
@Service
public class CartServiceImpl implements CartService{
    @Autowired
    CartDao cartDao;

    @Override
    public String insertItemInCart(Cart cart) {
        try {
            List<Cart> cart_items = this.cartDao.getItem(cart);

            if (!cart_items.isEmpty()) {
                return "Item already present in the cart.";
                //this.cartDao.updateQuantity(cart);
            } else if (cart.getQuantity() > 0) {
                this.cartDao.insertItemInCart(cart);
            }

            return "Item added to cart!";
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return "Some error occurred!";
    }

    @Override
    public CartDto getAllItemsInCart(User user) {
        List<Cart> cart_items = this.cartDao.getAllItemsInCart(user);
        int n = cart_items.size();
        int grand_total = 0;
        for(int i=0; i<n; i++){
            int qty = cart_items.get(i).getQuantity();
            int mrp = cart_items.get(i).getMRP();
            int total_price = qty*mrp;
            cart_items.get(i).setTotal_price(total_price);
            grand_total+= total_price;
        }
        CartDto cart = new CartDto(user.getId(), n, grand_total, cart_items);

        return cart;
    }

    @Override
    public String updateQuantity(Cart cart) {
        try {
            List<Cart> cart_items = this.cartDao.getItem(cart);

            if (cart_items.isEmpty()) {
                return "Item not present in the cart.";
                //this.cartDao.insertItemInCart(cart);
            } else if (cart.getQuantity() > 0) {
                this.cartDao.updateQuantity(cart);
            } else if (cart.getQuantity() > 0) {
                removeItemFromCart(cart);
            }

            return "Quantity updated!";
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "Some error occurred!";
    }

    @Override
    public String removeItemFromCart(Cart cart) {
        try {
            List<Cart> cart_items = this.cartDao.getItem(cart);

            if (!cart_items.isEmpty()) {
                this.cartDao.removeItemFromCart(cart);
                return "Item removed from cart!";
            }
            else{
                return "Item not present in the cart!";
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "Some error occurred!";
    }
}
