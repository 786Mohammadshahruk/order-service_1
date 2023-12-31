package com.order.service.impl;

import com.order.dto.OrderDto;
import com.order.dto.PaymentDto;
import com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PerformAsyncTask performAsyncTask;


    @Override
    public OrderDto createOrder(OrderDto order) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(getPaymentDto(order), httpHeaders);

        ResponseEntity<PaymentDto> value = restTemplate.exchange("http://localhost:8082/payment/create",
                HttpMethod.POST, httpEntity, PaymentDto.class);

        performAsyncTask.notifyUser(order);
        log.info("after notifyUser ");

        if (value.getStatusCode().value() == 200) {
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
    @Async
    public void notifyUser(OrderDto order) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Inside Notify User");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(getPaymentDto(order), httpHeaders);
        ResponseEntity<PaymentDto> value = restTemplate.exchange("http://localhost:8082/payment/create",
                HttpMethod.POST, httpEntity, PaymentDto.class);

        log.info("Response Data : {}", value.toString());

    }


}
