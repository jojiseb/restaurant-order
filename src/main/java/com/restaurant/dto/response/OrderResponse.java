package com.restaurant.dto.response;

import com.restaurant.enums;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private String id;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private enums.OrderStatus status;
    private String customerName;
    private String customerPhone;
    private LocalDateTime createdAt;
}
