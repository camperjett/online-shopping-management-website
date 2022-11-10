package com.WholeSailor.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class orderAction {
    Integer order_id;
    Integer old_status;
    Integer new_status;
}
