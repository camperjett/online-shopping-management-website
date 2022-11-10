package com.WholeSailor.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempModel {
    public Integer id;
    public Blob bloby;
    public String from_fd;
}
