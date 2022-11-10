package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class orderDetails {
    Integer oid;
    Integer order_id;
    Integer product_id;
    Integer final_price;
    Integer quantity;
}
