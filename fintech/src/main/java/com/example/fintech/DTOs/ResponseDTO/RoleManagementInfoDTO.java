package com.example.fintech.DTOs.ResponseDTO;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class RoleManagementInfoDTO {

    String id;

    String name;

    StatusEnum status;

    List<String> accessPermissions;

    String createdDate;

    String updatedDate;

    String createdUser;

    String updatedUser;

}
