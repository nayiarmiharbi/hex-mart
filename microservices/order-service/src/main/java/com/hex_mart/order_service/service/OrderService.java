package com.hex_mart.order_service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.hex_mart.order_service.dto.InventoryResponse;
import com.hex_mart.order_service.dto.OrderLineItemsDto;
import com.hex_mart.order_service.dto.OrderRequest;
import com.hex_mart.order_service.model.Order;
import com.hex_mart.order_service.model.OrderLineItems;
import com.hex_mart.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber("ORD-" + System.currentTimeMillis());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(orderLineItem -> mapToDto(orderLineItem)).toList();
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
            .map(orderLineItem -> orderLineItem.getSkuCode())
            .toList();

        // Call Inventory Service to check stock availability
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8083/api/inventory", 
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        // Check if all products are in stock
        boolean allProductInStock = Arrays.stream(inventoryResponses)
            .allMatch(InventoryResponse::getIsInStock);
        if (allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }
}
