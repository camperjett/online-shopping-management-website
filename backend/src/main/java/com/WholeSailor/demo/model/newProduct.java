package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class newProduct {
    private String product_name;
    private String description;
    private float MRP;
    private String brand;
    private int category_id;
}
