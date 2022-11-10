package com.WholeSailor.demo.controller;

import com.WholeSailor.demo.dao.CategoryDAO;
import com.WholeSailor.demo.dao.ProductDAO;
import com.WholeSailor.demo.model.CatProductFilter;
import com.WholeSailor.demo.model.Category;
import com.WholeSailor.demo.model.Product;
import com.WholeSailor.demo.model.catProducts;
import com.WholeSailor.demo.payload.SearchProductDTO;
import com.WholeSailor.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public List<Category> getAllCategory() {
        return categoryDAO.getAll();
    }



    @PostMapping("")
    public String postCategory(@RequestBody List<Category> list) {
        try {
            categoryDAO.batchCreate(list);
            return "created all";
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Some Fault";
    }

   /* @PostMapping("/{id}/products")
    public catProducts catWithProducts(@PathVariable int id, @RequestBody CatProductFilter filter) {
        catProducts result = new catProducts();
        Category cat = categoryDAO.getById(id);
        List<Product> products = productDAO.getByCIDFilter(id, filter);
        result.setProducts(products);
        result.setCid(cat.getCid());
        result.setTitle(cat.getTitle());
        result.setImage_path(cat.getImage_path());
        return result;
    }*/

    @PutMapping("/edit")
    public ResponseEntity<String> editCategory(@RequestBody Category category){
        boolean success = categoryDAO.editCategory(category);
        if(success)
            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Some error occurred.", HttpStatus.OK);
    }


    @PostMapping("/{category_id}/products")
    public catProducts getAllProducts(@PathVariable int category_id, @RequestBody SearchProductDTO product){
        catProducts result = new catProducts();

        product.setCategory_id(category_id);
        List<Product> products =  this.productService.getAllProductsCustom(product);

        Category cat = categoryDAO.getById(category_id);
        result.setProducts(products);
        result.setCid(cat.getCid());
        result.setTitle(cat.getTitle());
        result.setImage_path(cat.getImage_path());
        return result;
    }

    @GetMapping("/productCount/{cid}")
    List ProductCountPerCat(@PathVariable int cid) {
        List res = categoryDAO.productCount(cid);
        return res;
    }
}
