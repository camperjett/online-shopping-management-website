package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    public int review_id;
    public int user_id;
    public int product_id;
    public String title;
    public String text;
    public Float rating;
    public int upVotes;
    public int downVotes;
}
