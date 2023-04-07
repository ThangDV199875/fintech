package com.example.fintech.DTOs.RequestDTO;

import com.example.fintech.enums.ApiTargetEnum;
import lombok.Data;

import java.util.List;

@Data
public class ApiManagementRequestDTO {

    String name;

    List<ApiTargetEnum> apiTargets;

    List<String> roles;

}
