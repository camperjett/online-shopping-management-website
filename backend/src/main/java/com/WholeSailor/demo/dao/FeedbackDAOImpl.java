package com.WholeSailor.demo.dao;


import com.WholeSailor.demo.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {

    @Autowired
    JdbcTemplate template;

    @Override
    public Feedback getFeedByID(int fid, int user_id)
    {
        String sql = "SELECT * FROM feedback WHERE feed_id = ? AND user_id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Feedback.class), fid, user_id);
    }

    @Override
    public List<Feedback> getAllFeed(int user_id)
    {
        String sql = "SELECT * FROM feedback WHERE user_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Feedback.class), user_id);
    }

    @Override
    public  boolean insertFeedback(int user_id, Feedback feedback)
    {
        String sql = "INSERT INTO feedback(message, user_id, feed_date) VALUES(?, ?, NOW())";
        if(template.update(sql, new Object[] {feedback.getMessage(), user_id}) > 0) return true;
        return false;
    }

    @Override
    public boolean updateFeedback(int user_id, Feedback feedback, int fid) {
        if(feedback.getMessage() != null)
        {
            String sql = "UPDATE feedback SET message = ?, feed_date = NOW() WHERE user_id = ? AND feed_id = ?";
            if(template.update(sql, new Object[] {feedback.getMessage(), user_id, fid}) > 0) return true;
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteFeedback(int user_id, int fid)
    {
        String sql = "DELETE FROM feedback WHERE user_id = ? AND feed_id = ?";
        if(template.update(sql, new Object[] {user_id, fid}) > 0) return true;
        return false;
    }

}
