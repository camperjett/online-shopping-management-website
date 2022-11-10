package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.ReviewDao;
import com.WholeSailor.demo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao dao;

    public List<Review> ReadAllProductByProductId(int product_id){
        try {
            List<Review> ans = dao.ReadAllProductByProductId(product_id);
            if (ans.isEmpty())
                System.out.println("There is no review for this product_id");
            else
                System.out.println("get reviews for given product_id");
            return ans;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;



    }

}
