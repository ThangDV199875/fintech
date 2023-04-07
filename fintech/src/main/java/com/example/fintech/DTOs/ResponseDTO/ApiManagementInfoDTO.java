package com.example.fintech.DTOs.ResponseDTO;

import com.example.fintech.enums.ApiTargetEnum;
import com.example.fintech.enums.StatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class ApiManagementInfoDTO {

    String id;

    String name;

    List<ApiTargetEnum> apiTargets;

    List<String> roles;

    StatusEnum status;

    String createdDate;

    String updatedDate;

    String createdUser;

    String updatedUser;

}
