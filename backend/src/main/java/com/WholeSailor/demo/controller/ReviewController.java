package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.model.Review;
import com.WholeSailor.demo.model.customRequest;
import com.WholeSailor.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {


    @Autowired
    private ReviewService service;


    @GetMapping("/Review/{product_id}")
    public List<Review> ReadAllByProductByProductId(@PathVariable int product_id) {
        List<Review> ans = service.ReadAllProductByProductId(product_id);
        return ans;
    }
}
