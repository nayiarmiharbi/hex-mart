package com.hex_mart.order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber("ORD-" + System.currentTimeMillis());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(orderLineItem -> mapToDto(orderLineItem)).toList();
        order.setOrderLineItems(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }
}
