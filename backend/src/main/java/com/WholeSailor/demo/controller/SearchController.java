package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.SearchDAO;
import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchDAO searchDAO;


    //search by product name
    @PostMapping("/Product")
    ResponseEntity<List<Product>> SearchByKeyword(@RequestBody String keyword)
    {
        List<Product> pd = searchDAO.searchProductByName(keyword);
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }

    // search for categories
    @PostMapping("/category")
    ResponseEntity<List<Category>> SearchCategories(@RequestBody String keyword)
    {
        List<Category> cat = searchDAO.searchCategories(keyword);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    // search for product in a particular category
    @PostMapping("/{someCategory}")
    ResponseEntity<List<Product>> SearchInSomeCategory(@RequestBody String keyword, @PathVariable String someCategory)
    {
        List<Product> pd = searchDAO.searchInSomeCategory(keyword, someCategory);
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }

    // search for brand
   /* @PostMapping("/Brand")
    ResponseEntity<List<Product>> SearchBrands(@RequestBody String keyword)
    {

    }*/


    // search acc to price range
    @PostMapping("/cost")
    ResponseEntity<List<Product>> SearchByCost(@RequestBody List<Integer> range)
    {
        List<Product> pd = searchDAO.searchByPrice(range);
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }

    // search acc to rating
    @PostMapping("/rating")
    ResponseEntity<List<Product>> SearchByRating(@RequestBody List<Integer> range)
    {
        List<Product> pd = searchDAO.searchByRat(range);
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }

    // search acc to availability
    @PostMapping("/available/{val}")
    ResponseEntity<List<Product>> SearchAvailability(@PathVariable int val)
    {
        List<Product> pd = searchDAO.searchByAvail(val); // 0 - NA, 1 - A
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }

}

  /*  brand array
    price_low and price_high
        rating_low and rating_high
        availability
        */




























