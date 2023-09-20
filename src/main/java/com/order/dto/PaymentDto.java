package com.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    private Long orderId;
    private String paymentMode;
    private BigDecimal amount;
    private String transactionId;
}
