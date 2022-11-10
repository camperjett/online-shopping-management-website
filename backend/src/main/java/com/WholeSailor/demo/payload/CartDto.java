package com.WholeSailor.demo.payload;

import com.WholeSailor.demo.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    int user_id;
    int total_items;
    int grand_total;
    List<Cart> cart_items;
}
