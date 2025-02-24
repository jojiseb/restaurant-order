package com.restaurant.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {
    private String menuItemId;
    private String menuItemName;
    private BigDecimal pricePerItem;
    private Integer quantity;
    private BigDecimal subTotal;
}
