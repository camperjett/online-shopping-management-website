package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.FeedbackDAO;
import com.WholeSailor.demo.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class FeedbackController {

    @Autowired
    FeedbackDAO feedbackDAO;

    @GetMapping("feedback/{fid}")
    ResponseEntity <Feedback> FeedbackByID(@PathVariable int fid, @RequestHeader("user-id") int user_id)
    {
        Feedback fdb = feedbackDAO.getFeedByID(fid, user_id);
        return new ResponseEntity<>(fdb, HttpStatus.OK);
    }

    @GetMapping("feedback/")
    ResponseEntity<List<Feedback>> AllFeedbacks(@RequestHeader("user-id") int user_id)
    {
        List<Feedback> fdb = feedbackDAO.getAllFeed(user_id);
        return new ResponseEntity<>(fdb, HttpStatus.OK);
    }

    @PostMapping("feedback/")
    ResponseEntity<String> GiveFeedback(@RequestHeader("user-id") int user_id, @RequestBody Feedback feedback)
    {
        if(feedbackDAO.insertFeedback(user_id, feedback))
        {
            return new ResponseEntity<>("Your feedback has been saved", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Some problem has occured", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("feedback/{fid}")
    ResponseEntity<String> UpdateFeedback(@RequestHeader("user-id") int user_id, @RequestBody Feedback feedback, @PathVariable int fid)
    {
        if(feedbackDAO.updateFeedback(user_id, feedback, fid))
        {
            return new ResponseEntity<>("Your feedback has been updated", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Some problem has occured", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("feedback/{fid}")
    ResponseEntity<String> DeleteFeedback(@RequestHeader("user-id") int user_id, @PathVariable int fid)
    {
        if(feedbackDAO.deleteFeedback(user_id, fid))
        {
            return new ResponseEntity<>("Your feedback has been deleted", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Some problem has occured", HttpStatus.BAD_GATEWAY);
        }
    }
}
