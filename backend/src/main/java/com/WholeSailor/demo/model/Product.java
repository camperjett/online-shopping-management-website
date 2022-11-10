package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    public int product_id;
    public String product_name;
    public Date date_added;
    public Float avg_rating;
    public Integer no_of_reviews;
    public Integer stock;
    public String description;
    public Float MRP;
    public String brand;
    public Boolean availability;
    public Integer category_id;
    public String photo_url;

    public List<Review> reviews;
    public List<product_faq> productFaqs;


    public List<Picx> product_images;
}
