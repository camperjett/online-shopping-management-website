package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class shopkeeperReqDetails {
    public int user_id;
    public String username;
    public String password;
    public Timestamp date_joined;
    //    role: 1 - customer, 2 - shopkeeper, 3 - admin
    public int role;
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
}
