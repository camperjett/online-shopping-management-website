package com.WholeSailor.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class product_faq {
    public int question_id;
    public String answer;
    public String question_description;
    public  int product_id;
    public int upVotes;
    public int downVotes;

}
