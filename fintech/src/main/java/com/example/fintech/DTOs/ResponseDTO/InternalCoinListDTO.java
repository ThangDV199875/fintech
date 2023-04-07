package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class InternalCoinListDTO {
    List<InternalCoinInfoDTO> internalCoinInfoDTOS;

    int totalPage;

    long totalSize;
}
