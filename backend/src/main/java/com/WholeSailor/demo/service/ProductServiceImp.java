package com.WholeSailor.demo.service;


import com.WholeSailor.demo.dao.ProductDAO;
import com.WholeSailor.demo.dao.ReviewDao;
import com.WholeSailor.demo.dao.UserDAO;
import com.WholeSailor.demo.model.Product;
import com.WholeSailor.demo.model.Review;
import com.WholeSailor.demo.payload.SearchProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    UserDAO userDAO;

    @Override
    public int createNew(Product product) {
        try {
            int res = productDAO.exists(product.getProduct_name(), product.getCategory_id());
            if (res == 0) {
                productDAO.create(product);
                return 1;
            } else if (res == 1) {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public Product getByProductName(String product_name) {

        return productDAO.getByName(product_name);
    }

    @Override
    public Product getByProductID(int product_id) {
        return productDAO.getByID(product_id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    @Override
    public List<Product> getByProductCID(int category_id) {
        return productDAO.getByCID(category_id);
    }

    @Override
    public List<Product> getAllProductsCustom(SearchProductDTO product) {
        List<Product> products = productDAO.getAllProductCustom(product);
        System.out.println(products);
        int n = products.size();

        for (int i = 0; i < n; i++) {
            //Set availability status.
            int stocks = products.get(i).getStock();

            if (stocks > 0) {
                products.get(i).setAvailability(true);
            } else {
                products.get(i).setAvailability(false);
            }

            //Get and set all reviews
            List<Review> productReviews = this.reviewDao.ReadAllProductByProductId(products.get(i).getProduct_id());
            products.get(i).setReviews(productReviews);
        }

        return products;
    }

    @Override
    public String updateProduct(Product product, int user_id) {

        try {
            if (this.userDAO.getRole(user_id) != 3) {
                return "Operation not allowed!";
            }

            if (product.getProduct_name() != null) {
                productDAO.updateProductName(product);
            }
            if (product.getAvg_rating() != null) {
                productDAO.updateProductRating(product);
            }
            if (product.getStock() != null) {
                productDAO.updateProductStock(product);
            }
            if (product.getDescription() != null) {
                productDAO.updateProductDescription(product);
            }
            if (product.getMRP() != null) {
                productDAO.updateProductMRP(product);
            }
            if (product.getBrand() != null) {
                productDAO.updateProductBrand(product);
            }
            if (product.getCategory_id() != null) {
                productDAO.updateProductCategory(product);
            }
            if (product.getPhoto_url() != null) {
                productDAO.updatePhotoURL(product);
            }
            if (product.getProduct_images() != null) {
                productDAO.updateProductImages(product);
            }

            return "Updated product information!";
        } catch (Exception e) {
            System.out.println(e);
            return "Some error occured!";
        }
    }


}

