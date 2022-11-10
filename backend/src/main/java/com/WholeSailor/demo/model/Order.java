package com.WholeSailor.demo.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    Integer order_id;
    Integer status;
    Date date;
    Integer user_id;
    Integer area_id;
    Integer address_id;
    Integer transaction_id;

}
