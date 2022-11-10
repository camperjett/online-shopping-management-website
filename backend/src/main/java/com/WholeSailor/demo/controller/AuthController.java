package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.AddressDAO;
import com.WholeSailor.demo.dao.CategoryDAO;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.service.AdminService;
import com.WholeSailor.demo.service.CustomerService;
import com.WholeSailor.demo.service.ShopkeeperService;
import com.WholeSailor.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.sql.Blob;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ShopkeeperService shopkeeperService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserDAO userDAO;

    @Autowired
    AddressDAO addressDAO;
    @Autowired
    JdbcTemplate jd;
    @Autowired
    CategoryDAO catDAO;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        int res = 0;
        try {
            res = userService.register(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (res == -1) {
            return new ResponseEntity<>("username already taken", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Sorry some fault", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success! new user created", HttpStatus.OK);
        }
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        int res = customerService.register(customer);
        if (res == -1) {
            return new ResponseEntity<>("username already taken", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Sorry some fault", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success! new user created", HttpStatus.OK);
        }
    }

   /*@PostMapping("/signup/shopkeeper")
    public ResponseEntity<String> createShopkeeper(@RequestBody Shopkeeper shopkeeper) {
        int res = shopkeeperService.register(shopkeeper);
        if (res == -1) {
            return new ResponseEntity<>("username already taken", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Sorry some fault", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success! new user created", HttpStatus.OK);
        }
    }*/

    @PostMapping("/signup/shopkeeper")
    public ResponseEntity<String> createShopkeeper(@RequestBody ShopkeeperRegister shopkeeper) {
        int res = shopkeeperService.register(shopkeeper);
        if (res == -1) {
            return new ResponseEntity<>("username already taken", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Sorry some fault", HttpStatus.BAD_REQUEST);
        } else if (res == -2) {
            return new ResponseEntity<>("You are already a Shopkeeper", HttpStatus.BAD_REQUEST);
        } else if (res == -3) {
            return new ResponseEntity<>("Request already placed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success! new user created and Request for shopkeeper has been placed", HttpStatus.OK);
        }
    }

   /* @PostMapping("/signup/shopkeeper")
    public ResponseEntity<String> createShopkeeper(@RequestBody shopkeeper_request shop_req, @RequestHeader("user-id") int user_id)
    {
        int res = this.shopkeeperService.createNewShop(shop_req, user_id);
        if(res == -1)
        {
            return new ResponseEntity<>("You are already a Shopkeeper", HttpStatus.BAD_REQUEST);
        }
        else if(res == -2)
        {
            return new ResponseEntity<>("Request already placed", HttpStatus.BAD_REQUEST);
        }
        else if(res == 0)
        {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>("Your request has been sent.", HttpStatus.OK);
    }*/

    @PostMapping("/signup/admin")
    public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
        int res = adminService.register(admin);
        if (res == -1) {
            return new ResponseEntity<>("username already taken", HttpStatus.CONFLICT);
        } else if (res == 0) {
            return new ResponseEntity<>("Sorry some fault", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success! new user created", HttpStatus.OK);
        }
    }

    @PostMapping("/signin")
    public signInResponse signIn(@RequestBody User user, HttpSession session) {
        signInResponse response = new signInResponse();
        System.out.println(user);
        if (Objects.equals(user.getPassword(), "") || user.getRole() < 1 || user.getRole() > 3 || Objects.equals(user.getUsername(), "")) {
            response.setIsSuccess(0);
            return response;
        }
        Boolean exists = userDAO.exists(user.getUsername()),
                match = userDAO.match(user);
        if (!exists) {
            response.setIsSuccess(1);
        } else if (!match) {
            response.setIsSuccess(2);
        } else {
            response.setIsSuccess(3);
            response.setUser_id(userDAO.getByUsername(user.getUsername()).getId());
        }
        return response;
    }

//    @PostMapping("/test")
//    public String postImage(@RequestBody TempModel req) {
//        return addressDAO.addImg(req.getFrom_fd());
//    }

//    @GetMapping("/test/{id}")
//    public TempModel getImage(@PathVariable Integer id) {
//        return addressDAO.getImg(id);
//    }

    @PostMapping("/dev")
    public Picx processImg(@RequestBody Picx req) {
        try {
            catDAO.addCategoryImages(req.getId(), req.getImage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    @GetMapping("/listHeaders")
//    public ResponseEntity<String> listAllHeaders(
//            @RequestHeader Map<String, String> headers) {
//        headers.forEach((key, value) -> {
//            System.out.println(String.format("Header '%s' = %s", key, value));
//        });
//
//        return new ResponseEntity<String>(
//                String.format("Listed %d headers", headers.size()), HttpStatus.OK);
//    }
}

