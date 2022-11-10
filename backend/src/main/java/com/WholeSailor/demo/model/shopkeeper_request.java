package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class shopkeeper_request extends User {
    public int sid;
    public String first_name;
    public String last_name;
    public int gender;
    public String email;
    public String phone_no;
    public String photo_url;
    public List<Address> addresses;
    private String license_image_url;
    private String license_no;
    public int isApproved;
    // 0 - in waiting, 1 - approved, 2 - declined
}
