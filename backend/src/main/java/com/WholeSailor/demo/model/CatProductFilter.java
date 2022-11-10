package com.WholeSailor.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatProductFilter {
    List<String> brands;
    int price_low;
    int price_high;
    int rating_low;
    int rating_high;
    int availability;
    int discount;
}
