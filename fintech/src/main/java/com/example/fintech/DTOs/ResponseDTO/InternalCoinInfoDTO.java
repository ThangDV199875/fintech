package com.example.fintech.DTOs.ResponseDTO;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;

@Data
public class InternalCoinInfoDTO {

    String id;

    Long coin;

    String partnerName;

    String userName;

    StatusEnum status;

    String createdDate;

    String updatedDate;

    String createdUser;

    String updatedUser;

}
