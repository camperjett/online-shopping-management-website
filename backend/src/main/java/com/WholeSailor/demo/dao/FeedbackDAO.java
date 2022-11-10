package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Feedback;

import java.util.List;

public interface FeedbackDAO {
    Feedback getFeedByID(int fid, int user_id);

    List<Feedback> getAllFeed(int user_id);

    boolean insertFeedback(int user_id, Feedback feedback);

    boolean updateFeedback(int user_id, Feedback feedback, int fid);

    boolean deleteFeedback(int user_id, int fid);
}
