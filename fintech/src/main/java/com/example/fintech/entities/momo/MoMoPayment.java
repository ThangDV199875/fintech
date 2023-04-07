package com.example.fintech.entities.momo;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "momo_payment")
@Data
public class MoMoPayment {

    @Id
    ObjectId id;

    String partnerCode;

    String orderId;

    String requestId;

    Long amount;

    String responseTime;

    String message;

    Integer resultCode;

    String payUrl;

    StatusEnum status;

    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

    @CreatedBy
    ObjectId createdUser;

}
