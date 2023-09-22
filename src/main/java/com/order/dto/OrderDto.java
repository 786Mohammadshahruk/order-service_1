package com.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {

    private Long orderId;
    private String productName;
    private int quantity;
    private String brand;
    private String paymentMode;
    private BigDecimal amount;
    private String transactionId;
    private String mobileNumber;
    private String email;
}
