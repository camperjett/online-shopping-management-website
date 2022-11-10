package com.WholeSailor.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class product_discount {
    private int discount_id;
    private int product_id;
    private int min_qty;
    private int max_qty;
    private float discount_percentage;
}
