package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class transaction {
    public int transaction_id;
    public String date_of_tran;
    public int amount;
    public int mode;
    public String account_no;
    public String bank_name;
    public String bank_branch;
    public int verification_status;
}