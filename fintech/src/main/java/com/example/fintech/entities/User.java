package com.example.fintech.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "user")
@Data
public class User {

    @Id
    ObjectId id;

    String name;

    String username;

    String password;

    String email;

    String phone;

    List<String> roles;

    @CreatedDate
    LocalDateTime createdDate;

    @LastModifiedDate
    LocalDateTime updatedDate;

    @CreatedBy
    ObjectId createdUser;

    ObjectId updatedUser;

}
