package com.WholeSailor.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductDTO {
    int category_id;
    List<String> brands;
    int price_low;
    int price_high;
    int rating_low;
    int rating_high;
    Boolean isAvailable;
    int discount;
}
