package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    int feed_id;
    private String message;
    private String reply;
    public Timestamp feed_date;
    private Integer reviewed_by;
    private Integer user_id;
}
