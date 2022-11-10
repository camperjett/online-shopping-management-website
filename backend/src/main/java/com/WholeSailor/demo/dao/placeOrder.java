package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.productForOrder;
import com.WholeSailor.demo.model.transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class placeOrder {
    transaction tran;
    List<productForOrder> items;
    int ad_id;
}

