package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public int id;
    public String username;
    public String password;
    public Timestamp date_joined;
    //    role: 1 - customer, 2 - shopkeeper, 3 - admin
    public int role;

//    public List<Address> add;

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}



