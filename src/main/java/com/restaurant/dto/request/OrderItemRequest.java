package com.restaurant.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderItemRequest {
    @NotNull(message = "Menu item id is required")
    private String menuItemId;

    @NotNull(message = "Quantity is required")
//    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;


}
