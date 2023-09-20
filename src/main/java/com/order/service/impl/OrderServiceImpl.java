package com.order.service.impl;

import com.order.dto.OrderDto;
import com.order.dto.PaymentDto;
import com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public OrderDto createOrder(OrderDto order) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(getPaymentDto(order), httpHeaders);

        ResponseEntity<PaymentDto> value = restTemplate.exchange("http://localhost:8082/payment/create",
                HttpMethod.POST, httpEntity, PaymentDto.class);
        if(value.getStatusCode().value()==200) {
            log.info("Response: {}", value.getBody());
        }
        return order;
    }

    private PaymentDto getPaymentDto(OrderDto order) {

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setPaymentMode(order.getPaymentMode());
        paymentDto.setOrderId(order.getOrderId());
        paymentDto.setTransactionId(order.getTransactionId());
        paymentDto.setAmount(order.getAmount());
        return paymentDto;
    }
}
