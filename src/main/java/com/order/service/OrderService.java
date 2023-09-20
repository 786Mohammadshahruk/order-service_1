package com.order.service;

import com.order.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto order);
}
