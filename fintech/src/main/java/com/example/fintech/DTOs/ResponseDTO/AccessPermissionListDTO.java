package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class AccessPermissionListDTO {

    List<AccessPermissionInfoDTO> accessPermissionInfoDTOS;

    int totalPage;

    long totalSize;

}
