package com.example.fintech.entities;

import com.example.fintech.enums.ApiTargetEnum;
import com.example.fintech.enums.StatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "api_management")
@Data
public class ApiManagement {

    @Id
    ObjectId id;

    String name;

    List<ApiTargetEnum> apiTargets;

    List<String> roles;

    StatusEnum status;

    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

    @CreatedBy
    ObjectId createdUser;

    ObjectId updatedUser;
}
