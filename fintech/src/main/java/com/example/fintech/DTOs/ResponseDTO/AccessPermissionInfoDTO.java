package com.example.fintech.DTOs.ResponseDTO;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;

@Data
public class AccessPermissionInfoDTO {

    String id;

    String name;

    StatusEnum status;

    String createdDate;

    String updatedDate;

    String createdUser;

    String updatedUser;

}
