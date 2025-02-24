package com.restaurant.controller;

import com.restaurant.dto.request.MenuItemRequest;
import com.restaurant.dto.response.ApiResponse;
import com.restaurant.dto.response.MenuItemResponse;
import com.restaurant.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MenuItemController {

    private final MenuItemService menuItemService;

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);


    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping(value = "/menu-items")
    public ResponseEntity<ApiResponse<MenuItemResponse>> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        try {
            MenuItemResponse createdItem = menuItemService.createMenuItem(request);
            ApiResponse<MenuItemResponse> response = ApiResponse.ok(createdItem);
            response.setMessage("Menu Item created successfully !!");
            response.setTotalResultCount(1);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
        }
        catch(Exception ex) {
            logger.error("Error creating menu item: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error Creating Menu item : "+ex.getMessage()));
        }
    }

    @GetMapping(value = "/menu-items")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getAllMenuItems(@RequestParam(required = false) String category) {
        try {
            List<MenuItemResponse> items = menuItemService.getAllMenuItems(category);
            ApiResponse<List<MenuItemResponse>> response = new ApiResponse<>(items, items.size());

            if (category != null && !category.isEmpty()) {
                response.setMessage("Retrieved "+items.size()+" menu items in "+category+" category");
                response.setTotalResultCount(items.size());
            } else {
                response.setMessage("Retrieved "+ items.size()+" menu items");
                response.setTotalResultCount(items.size());
            }

            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error Fetching menu Items: "+ex.getMessage()));
        }
    }
}
