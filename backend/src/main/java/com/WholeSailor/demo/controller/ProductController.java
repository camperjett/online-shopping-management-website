package com.WholeSailor.demo.controller;


import com.WholeSailor.demo.dao.ProductDAO;
import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.payload.SearchProductDTO;
import com.WholeSailor.demo.service.ProductService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    private ProductDAO productDAO;

    @PostMapping("/all")
    public List<Product> getAllProducts(@RequestBody SearchProductDTO product) {
        return this.productService.getAllProductsCustom(product);
    }

    @GetMapping("/name/{product_name}")
    public Product getProductByName(@PathVariable String product_name) {
        return productService.getByProductName(product_name);
    }

    @GetMapping("/id/{product_id}")
    public Product getProductByID(@PathVariable Integer product_id) {
        return productService.getByProductID(product_id);
    }

//    @GetMapping("/")
//    public List<Product> getAllProducts(@RequestBody requestProducts body) {
//
//    }

    @GetMapping("/category/Category+{category_id}")
    public List<Product> getProductByCID(@PathVariable Integer category_id) {
        return productService.getByProductCID(category_id);
    }


    @PostMapping("")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        int res = productService.createNew(product);
        if (res == -1) {
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Some Problem occuring", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        int res = productDAO.delete(id);
        return res == 1 ? "Successfully deleted" : "can not delete";
    }


    @PostMapping("/{id}/faq/add")
    public ResponseEntity<String> addQuestion(@PathVariable int id, @RequestBody questions question) {
        product_faq faq = new product_faq();
        faq.setQuestion_description(question.getQuestion_description());
        faq.setUpVotes(question.getUpVotes());
        faq.setDownVotes(question.getDownVotes());
        int ans = productDAO.addQuestion(id, faq);
        if (ans == 0) {
            return new ResponseEntity<>("Some Problem occuring", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("question description added successfully for this product", HttpStatus.CREATED);
        }
    }


    @DeleteMapping("/{id}/faq/remove/{qid}")
    public String removeQuestion(@PathVariable int id, @PathVariable int qid) {
        int ans = productDAO.removeQuestion(id, qid);
        if (ans == 0) {
            return "Some Problem occuring";
        } else {
            return "Product question remove successfully";

        }
    }


    @PutMapping("/{id}/faq/answer/{qid}")
    public String giveAnswer(@PathVariable int id, @PathVariable int qid, @RequestBody answer ans) {
        product_faq faq = new product_faq();
        faq.setAnswer(ans.getAnswer());
        if (productDAO.giveAnswer(id, qid, faq) > 0)
            return "answer added for given question";
        else
            return "some error occure";

    }

    @PostMapping("/{id}/review/add")
    public ResponseEntity<String> ReviewAdd(@PathVariable int id, @RequestBody review_desc review, @RequestHeader("user_id") int user_id) {
        Review ans = new Review();
        ans.setTitle(review.getTitle());
        ans.setText(review.getText());
        ans.setUpVotes(review.getUpVotes());
        ans.setDownVotes(review.getDownVotes());

        int count = productDAO.ReviewAdd(id, ans, user_id);
        if (count == 0) {
            return new ResponseEntity<>("Some Problem occuring", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Product review added successfully", HttpStatus.CREATED);

        }
    }


    @PutMapping("/{id}/review/{rid}/rate")
    public String product_rating(@PathVariable int id, @PathVariable int rid, @RequestBody review_rating rate) {
        Review ans = new Review();
        ans.setRating(rate.getRating());
        int count = productDAO.rating(id, rid, ans);
        if (count == 0) {
            return "Some Problem occuring";
        } else {
            return "Product rating added successfully";
        }
    }


    @DeleteMapping("/{id}/review/{rid}/remove")
    public String Delete_review(@PathVariable int id, @PathVariable int rid) {
        int ans = productDAO.removeReview(id, rid);
        if (ans == 0) {
            return "Some Problem occuring";
        } else {
            return "review remove successfully for this product";

        }
    }

    @GetMapping("/{id}")
    public Product productDetail(@PathVariable int id) {
        Product ans = productDAO.productDetail(id);
        return ans;

    }


    //enter 1 for upvote and 0 for downvote in path variable vote.
    @PutMapping("/faq/{qid}/vote/{vote}")
    public String votingFAQ(@PathVariable int qid, @PathVariable Integer vote ){

        int ans = productDAO.votingFAQ(qid, vote);
        if(ans == 1)
            return "voting for particular question is done";
        else
            return "something went wrong";

    }

    @PutMapping("/review/{rid}/vote/{vote}")
    public String votingREVIEW(@PathVariable int rid, @PathVariable Integer vote ){

        int ans = productDAO.votingREVIEW(rid, vote);
        if(ans == 1)
            return "voting for particular question is done";
        else
            return "something went wrong";

    }





}
