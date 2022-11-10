package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    public String cid;
    public String first_name;
    public String last_name;
    //    add field for image
    public int gender;
    public String email;
    public String phone_no;
    public String photo_url;
    public List<Address> addresses;
}