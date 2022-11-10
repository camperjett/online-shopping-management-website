package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.AddressDAO;
import com.WholeSailor.demo.dao.ReviewDao;
import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @Autowired
    AddressDAO addressDAO;
    @Autowired
    ReviewDao reviewDAO;

    @GetMapping("/profile")
    ResponseEntity<Dashboard> showDashboard(@RequestHeader("user-id") Integer user_id) {
        try {
            User user = new User();
            user.setId(user_id);
            Dashboard dashboard = this.dashboardService.getDashboardInfo(user);
            return new ResponseEntity<>(dashboard, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
        }
        Dashboard dashboard = null;
        return new ResponseEntity<>(dashboard, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/profile/edit")
    ResponseEntity<String> editDashboard(@RequestBody Dashboard dashboard, @RequestHeader("user-id") int user_id) {
        /**
         * updates: username, first_name, last_name, gender, photourl
         * */
        dashboard.setUser_id(user_id);
        String res = this.dashboardService.updateDashboardInfo(dashboard);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/profile/edit/addEmail")
    ResponseEntity<String> editDashboardAddEmail(@RequestBody Email email, @RequestHeader("user-id") int user_id) {
        email.setUser_id(user_id);
        String res = this.dashboardService.addEmail(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/profile/edit/addContact")
    ResponseEntity<String> editDashboardAddContact(@RequestBody Contact contact, @RequestHeader("user-id") int user_id) {
        contact.setUser_id(user_id);
        String res = this.dashboardService.addContact(contact);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/profile/edit/deleteEmail/{ueid}")
    ResponseEntity<String> editDashboardDeleteEmail(@PathVariable Integer ueid, @RequestHeader("user-id") int user_id) {
        String res = this.dashboardService.removeEmail(ueid);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/profile/edit/deleteContact/{ucid}")
    ResponseEntity<String> editDashboardDeleteContact(@PathVariable Integer ucid, @RequestHeader("user-id") int user_id) {
        String res = this.dashboardService.removeContact(ucid);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/address")
    ResponseEntity<List<Address>> showAddress(@RequestHeader("user-id") Integer user_id) {
        List<Address> addresses = this.addressDAO.getAllAddress(user_id);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/address/add")
    ResponseEntity<String> addAddress(@RequestHeader("user-id") Integer user_id, @RequestBody Address address) {
        address.setUser_id(user_id);
        String res = this.addressDAO.addAddress(address);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/address/edit/{address_id}")
    ResponseEntity<String> editAddress(@RequestHeader("user-id") Integer user_id, @RequestBody Address address, @PathVariable Integer address_id) {
        address.setAddress_id(address_id);
        address.setUser_id(user_id);
        String res = this.addressDAO.editAddress(address);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/address/remove/{address_id}")
    ResponseEntity<String> removeAddress(@RequestHeader("user-id") Integer user_id, @PathVariable Integer address_id) {
        String res = this.addressDAO.removeAddress(address_id, user_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/review")
    ResponseEntity<List<Review>> showReviews(@RequestHeader("user-id") Integer user_id) {
        List<Review> reviews = this.reviewDAO.getAllReview(user_id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/review/edit/{review_id}")
    ResponseEntity<String> editReview(@RequestBody Review review, @PathVariable Integer review_id, @RequestHeader("user-id") Integer user_id) {
        review.setReview_id(review_id);
        review.setUser_id(user_id);
        String res = this.reviewDAO.editReview(review);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/review/remove/{review_id}")
    ResponseEntity<String> removeReview(@PathVariable Integer review_id, @RequestHeader("user-id") Integer user_id) {
        Review review = new Review();
        review.setUser_id(user_id);
        review.setReview_id(review_id);
        String res = this.reviewDAO.removeReview(review);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
