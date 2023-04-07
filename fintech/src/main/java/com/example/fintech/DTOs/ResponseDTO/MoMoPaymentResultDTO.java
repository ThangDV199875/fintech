package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

@Data
public class MoMoPaymentResultDTO {
    String partnerCode;

    String secretKey;

    String accessKey;

    String publicKey;

    String orderInfo;

    String amount;

    String token;

    String partnerClientId;

    String extraData;
}
