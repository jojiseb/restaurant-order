package com.restaurant;

import com.restaurant.controller.MenuItemController;
import com.restaurant.controller.OrderController;
import com.restaurant.dto.request.MenuItemRequest;
import com.restaurant.dto.request.OrderRequest;
import com.restaurant.dto.response.MenuItemResponse;
import com.restaurant.dto.response.OrderResponse;
import com.restaurant.service.MenuItemService;
import com.restaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MenuItemController.class, OrderController.class})
public class RestaurantOrderApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MenuItemService menuItemService;

	@MockBean
	private OrderService orderService;

	@Test
	public void testCreateMenuItem_Success() throws Exception {
		// Prepare test data
		MenuItemRequest request = new MenuItemRequest();
		request.setName("Test Pizza");
		request.setPrice(BigDecimal.valueOf(12.99));
		request.setAvailableQuantity(50);
		request.setCategory(enums.MenuCategory.MAIN_COURSE);

		// Mock service response
		MenuItemResponse mockResponse = new MenuItemResponse();
		mockResponse.setId(String.valueOf(1L));
		mockResponse.setName("Test Pizza");
		mockResponse.setPrice(BigDecimal.valueOf(12.99));
		mockResponse.setCategory(String.valueOf(enums.MenuCategory.MAIN_COURSE));

		when(menuItemService.createMenuItem(any(MenuItemRequest.class))).thenReturn(mockResponse);

		// Perform test
		mockMvc.perform(post("/api/menu-items")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Test Pizza\",\"price\":12.99,\"availableQuantity\":50,\"category\":\"MAIN_COURSE\"}")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.object.name").value("Test Pizza"))
				.andExpect(jsonPath("$.object.price").value(12.99));
	}

	@Test
	public void testGetMenuItems_FilterByCategory() throws Exception {
		// Mock service response
		when(menuItemService.getAllMenuItems("MAIN_COURSE")).thenReturn(Collections.emptyList());

		// Perform test
		mockMvc.perform(get("/api/menu-items")
						.param("category", "MAIN_COURSE")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.objects").isArray());
	}

	@Test
	public void testCreateOrder_Success() throws Exception {
		// Prepare test data
		OrderRequest request = new OrderRequest();
		request.setCustomerName("John Doe");
		request.setCustomerPhone("1234567890");
		request.setItems(Collections.emptyList());

		// Mock service response
		OrderResponse mockResponse = new OrderResponse();
		mockResponse.setId("1");
		mockResponse.setCustomerName("John Doe");
		mockResponse.setItems(Collections.emptyList());

		when(orderService.createOrder(any(OrderRequest.class))).thenReturn(mockResponse);

		// Perform test
		mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"customerName\":\"John Doe\",\"customerPhone\":\"1234567890\",\"items\":[]}")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.object.customerName").value("John Doe"));
	}

	@Test
	public void testGetOrder_Success() throws Exception {
		// Mock service response
		OrderResponse mockResponse = new OrderResponse();
		mockResponse.setId("1");
		mockResponse.setCustomerName("Jane Doe");

		when(orderService.getOrder("1")).thenReturn(mockResponse);

		// Perform test
		mockMvc.perform(get("/api/orders/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.object.id").value("1"))
				.andExpect(jsonPath("$.object.customerName").value("Jane Doe"));
	}
}