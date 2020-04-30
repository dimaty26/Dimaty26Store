package com.zmeev.oauth2Demo.dto;

import com.zmeev.oauth2Demo.entities.Product;
import lombok.Data;

@Data
public class OrderProductDto {

    private Product product;
    private Integer quantity;
}
