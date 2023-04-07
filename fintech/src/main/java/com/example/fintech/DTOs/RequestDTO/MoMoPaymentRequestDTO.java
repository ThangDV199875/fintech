package com.example.fintech.DTOs.RequestDTO;

import lombok.Data;

@Data
public class MoMoPaymentRequestDTO {
    String partnerCode;

    String orderInfo;

    String amount;

    String token;

    String partnerClientId;

    String extraData;
}
