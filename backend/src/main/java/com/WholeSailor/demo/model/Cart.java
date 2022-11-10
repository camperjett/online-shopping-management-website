package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Integer cart_id;
    private Integer user_id;
    private Integer product_id;
    private Integer quantity;
    private Integer MRP;
    private Integer total_price;
}