package com.example.fintech.DTOs.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class RoleManagementListDTO {

    List<RoleManagementInfoDTO> roleManagementInfoDTOS;

    int totalPage;

    long totalSize;

}
