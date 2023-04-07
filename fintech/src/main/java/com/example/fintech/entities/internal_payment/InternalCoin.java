package com.example.fintech.entities.internal_payment;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "internal_coin")
@Data
public class InternalCoin {

    @Id
    ObjectId id;

    Long coin;

    ObjectId userId;

    ObjectId partnerId;

    StatusEnum status;

    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

    @CreatedBy
    ObjectId createdUser;

    ObjectId updatedUser;

}
