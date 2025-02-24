package com.restaurant.entity;

import com.restaurant.enums;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Getter
@Setter
public class MenuItem {

    @Id
    private String id;

    private String name;
    private BigDecimal price;
    private Integer availableQuantity;
    private enums.MenuCategory category;


}
