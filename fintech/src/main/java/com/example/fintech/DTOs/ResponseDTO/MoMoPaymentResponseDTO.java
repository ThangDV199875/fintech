package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

@Data
public class MoMoPaymentResponseDTO {
    String partnerCode;

    String orderId;

    String requestId;

    Long amount;

    String responseTime;

    String message;

    Integer resultCode;

    String payUrl;
}
