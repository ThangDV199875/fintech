package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class ApiManagementListDTO {
    List<ApiManagementInfoDTO> apiManagementInfoDTOS;

    int totalPage;

    long totalSize;
}
