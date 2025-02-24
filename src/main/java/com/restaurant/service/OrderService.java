package com.restaurant.service;

import com.restaurant.dto.request.OrderItemRequest;
import com.restaurant.dto.request.OrderRequest;
import com.restaurant.dto.response.OrderItemResponse;
import com.restaurant.dto.response.OrderResponse;
import com.restaurant.entity.MenuItem;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.enums;
import com.restaurant.exception.InvalidRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public OrderResponse createOrder(OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not Found for : "+itemRequest.getMenuItemId()));

            if(menuItem.getAvailableQuantity() < itemRequest.getQuantity()) {
                throw new InvalidRequestException("Insufficient Quantity for "+menuItem.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubTotal(menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubTotal());

            menuItem.setAvailableQuantity(menuItem.getAvailableQuantity() - itemRequest.getQuantity());
            menuItemRepository.save(menuItem);
        }

        Order order = new Order();
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(enums.OrderStatus.RECEIVED);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return entityToOrderBean(savedOrder);
    }

    public OrderResponse getOrder(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found for : "+id));
        return entityToOrderBean(order);
    }

    private OrderResponse entityToOrderBean(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerPhone(order.getCustomerPhone());
        response.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponse> orderItemResponses = order.getItems()
                .stream()
                .map(this::entityToOrderItemBean)
                .collect(Collectors.toList());

        response.setItems(orderItemResponses);
        return response;
    }


    private OrderItemResponse entityToOrderItemBean(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setMenuItemId(orderItem.getMenuItem().getId());
        response.setMenuItemName(orderItem.getMenuItem().getName());
        response.setPricePerItem(orderItem.getMenuItem().getPrice());
        response.setQuantity(orderItem.getQuantity());
        response.setSubTotal(orderItem.getSubTotal());
        return response;
    }


}
