package com.restaurant.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private Integer availableQuantity;
    private String category;

}
