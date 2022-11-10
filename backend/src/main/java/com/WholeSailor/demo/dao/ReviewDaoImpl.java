package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {
    @Autowired
    private JdbcTemplate jdbc;


    public List<Review> ReadAllProductByProductId(int product_id) {
        String qry = "Select * from Review Where product_id=?";
        List<Review> review = jdbc.query(qry, new BeanPropertyRowMapper<Review>(Review.class), product_id);
        return review;
    }

    @Override
    public List<Review> getAllReview(Integer user_id) {
        String sql = "select * from Review where user_id=?";
        return this.jdbc.query(sql, new BeanPropertyRowMapper<>(Review.class), new Object[]{user_id});
    }

    @Override
    public String editReview(Review review) {
        String sql_text = "update Review set text = ? where user_id=? and review_id=?";
        String sql_title = "update Review set title=? where user_id=? and review_id=?";
        String sql_rating = "update Review set ratings = ? where user_id=? and review_id=?";

        try {
            if (review.getText() != null) {
                this.jdbc.update(sql_text, new Object[]{review.getText(), review.getUser_id(), review.getReview_id()});
            }
            if (review.getTitle() != null) {
                this.jdbc.update(sql_title, new Object[]{review.getTitle(), review.getUser_id(), review.getReview_id()});
            }
            if (review.getRating() != null) {
                this.jdbc.update(sql_rating, new Object[]{review.getRating(), review.getUser_id(), review.getReview_id()});
            }

            return "Updated successfully!";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occurred.";
    }

    @Override
    public String removeReview(Review review) {
        String sql = "delete from review where user_id=? and review_id=?";

        try {
            this.jdbc.update(sql, new Object[]{review.getUser_id(), review.getReview_id()});
            return "Deletion successful!";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occurred!";
    }

}
