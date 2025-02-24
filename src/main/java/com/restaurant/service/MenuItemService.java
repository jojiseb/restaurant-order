package com.restaurant.service;

import com.restaurant.dto.request.MenuItemRequest;
import com.restaurant.dto.response.MenuItemResponse;
import com.restaurant.entity.MenuItem;
import com.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setAvailableQuantity(request.getAvailableQuantity());
        menuItem.setCategory(request.getCategory());
        MenuItem savedItem = menuItemRepository.save(menuItem);
        return entityToBean(savedItem);
    }

    private MenuItemResponse entityToBean(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setPrice(menuItem.getPrice());
        response.setAvailableQuantity(menuItem.getAvailableQuantity());
        response.setCategory(menuItem.getCategory().name());
        return response;
    }

    public List<MenuItemResponse> getAllMenuItems(String category) {
        List<MenuItem> items;
        if(category != null && !category.isEmpty()) {
            items = menuItemRepository.findByCategory(category);
        }
        else {
            items = menuItemRepository.findAll();
        }

        return items.stream()
                .map(this::entityToBean)
                .collect(Collectors.toList());
    }
}
