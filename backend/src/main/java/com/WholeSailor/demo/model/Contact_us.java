package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Contact_us {
    private int cid;
    private String contact_no;
    private String email;
    private String social_media_account;
}