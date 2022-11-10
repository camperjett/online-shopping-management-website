package com.WholeSailor.demo.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
    public Integer user_id;
    public String username;
    public String first_name;
    public String last_name;
    public int role;
    //    add field for image
    public int gender;
    public String photo_url;
    public String phone_no;
    public String email;
    public List<Contact> secondary_contact_no;
    public List<Email> secondary_emails;
    private String license_image_url;
    private String license_no;
}
