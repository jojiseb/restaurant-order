package com.restaurant.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class OrderRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer phone is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid")
    private String customerPhone;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
//    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderItemRequest> items;

}
