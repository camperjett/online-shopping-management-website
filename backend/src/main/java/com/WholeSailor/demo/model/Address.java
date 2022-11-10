package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Address {
    int address_id;
    String pincode;
    String house_address;
    String city;
    int state;
    int user_id;
    String name;
}
