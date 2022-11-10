package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewDao {

    List<Review> ReadAllProductByProductId(int product_id);

    List<Review> getAllReview(Integer user_id);

    String editReview(Review review);

    String removeReview(Review review);
}

