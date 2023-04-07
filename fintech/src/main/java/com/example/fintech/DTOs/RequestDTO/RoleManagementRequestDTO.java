package com.example.fintech.DTOs.RequestDTO;

import lombok.Data;

import java.util.List;

@Data
public class RoleManagementRequestDTO {

    String name;

    List<String> accessPermissions;

}
