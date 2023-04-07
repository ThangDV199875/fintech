package com.example.fintech.entities;

import com.example.fintech.enums.StatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "refresh_token")
@Data
public class RefreshToken {

    @Id
    ObjectId id;

    @Indexed
    ObjectId userId;

    StatusEnum status;

    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

}
