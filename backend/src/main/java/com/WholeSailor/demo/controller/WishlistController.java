package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.WishlistDAO;
import com.WholeSailor.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistDAO wishlistDAO;

    @GetMapping("/")
    ResponseEntity<List<Product>> GetAllinWish(@RequestHeader("user-id") int user_id)
    {
        List<Product> pr = wishlistDAO.getAllinWish(user_id);
        return new ResponseEntity<>(pr, HttpStatus.OK);
    }

    @PostMapping("/add/{product_id}")
    ResponseEntity<String> AddNewWish(@RequestHeader("user-id") int user_id, @PathVariable int product_id)
    {
        int res = wishlistDAO.addNewWish(user_id, product_id);
        if(res == -1)
        {
            return new ResponseEntity<>("Your wish already exists", HttpStatus.CREATED);
        }
        else if (res == 1) {
            return new ResponseEntity<>("Your wish has been added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping("/remove/{product_id}")
    ResponseEntity<String> RemoveWish(@RequestHeader("user-id") int user_id, @PathVariable int product_id)
    {
        int res = wishlistDAO.removeWish(user_id, product_id);
        if (res == 1) {
            return new ResponseEntity<>("Your wish has been removed", HttpStatus.CREATED);
        }
        else if(res == -1){
            return new ResponseEntity<>("No such wish exists", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_GATEWAY);
    }


}
