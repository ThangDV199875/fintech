package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.MoMoPaymentRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.MoMoPaymentResponseDTO;

public interface MoMoService {
    MoMoPaymentResponseDTO createTransaction(MoMoPaymentRequestDTO requestDTO);
}
