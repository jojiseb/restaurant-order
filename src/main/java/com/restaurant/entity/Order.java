package com.restaurant.entity;

import com.restaurant.enums;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private enums.OrderStatus status;
    private String customerName;
    private String customerPhone;
    private LocalDateTime createdAt;
}
