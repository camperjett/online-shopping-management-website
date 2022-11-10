package com.WholeSailor.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class review_desc {
    public String title;
    public String text;
    public int upVotes;
    public int downVotes;

}
