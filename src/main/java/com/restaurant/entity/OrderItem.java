package com.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private MenuItem menuItem;
    private Integer quantity;
    private BigDecimal subTotal;

}
